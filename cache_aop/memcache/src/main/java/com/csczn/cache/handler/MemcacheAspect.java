package com.csczn.cache.handler;

import com.csczn.cache.annotation.MemCache;
import com.csczn.cache.service.IMemcachedService;
import com.csczn.cache.util.ReflectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * handler of cache
 *
 * @date 05/06/2015
 * @author wangcheng
 */
@Component
@Aspect
public class MemcacheAspect {
    private static Log logger = LogFactory.getLog(MemcacheAspect.class);
    @Resource
    IMemcachedService memcachedService;

    /**
     * check mode
     *
     * @param pjp
     * @param memCache
     * @return
     * @throws Throwable
     */
    @Around("@annotation(memCache)")
    public Object process(ProceedingJoinPoint pjp, MemCache memCache) throws Throwable {
        Object result;
            CacheKey[] cacheKeys;
            try {
                cacheKeys = splitExpression(memCache.expression(), memCache.expire());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                cacheKeys = null;
            }
            if (memCache.mode() == MemCache.Mode.CACHE) {
                result = cache(pjp, cacheKeys);
            } else if (memCache.mode() == MemCache.Mode.EVICT) {
                result = pjp.proceed();
                evict(pjp, cacheKeys);
            } else {
                result = pjp.proceed();
            }
        return result;
    }

    /**
     * retrieve record
     * 1. try to find in cache, if found return
     * 2. load from db
     * 3. save to cache
     * 4. return result
     *
     * @param pjp
     * @param cacheKeys
     * @return
     * @throws Throwable
     */
    public Object cache(ProceedingJoinPoint pjp, CacheKey[] cacheKeys) throws Throwable {
        Object result = null;
        String key = null;
        try {
            Method method = getMethod(pjp);
            key = parseKey(cacheKeys[0].unresolvedKey, method, pjp.getArgs());
            result = memcachedService.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (result == null) {
            logger.debug("cache not hit: " + key);
            result = pjp.proceed();
            if (result != null && StringUtils.isNotEmpty(key)) {
                try {
                    memcachedService.set(key, result, cacheKeys[0].expire);
                    logger.debug("cache set: " + key);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            logger.debug("cache hit: " + key);
        }

        return result;
    }

    /**
     * evict record
     *
     * @param pjp
     * @param cacheKeys
     */
    public void evict(ProceedingJoinPoint pjp, CacheKey[] cacheKeys) {
        try {
            Method method = getMethod(pjp);
            for (CacheKey cacheKey : cacheKeys) {
                String key = parseKey(cacheKey.unresolvedKey, method, pjp.getArgs());
                memcachedService.del(key);
                logger.debug("cache deleted: " + key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * get the method which is intercepted
     *
     * @param pjp
     * @return
     */
    public Method getMethod(ProceedingJoinPoint pjp) {
        Method[] methods = pjp.getTarget().getClass().getMethods();
        ReflectiveMethodInvocation methodInvocation = (ReflectiveMethodInvocation) ReflectUtil.getFieldValue(pjp, "methodInvocation");
        Method method = (Method) ReflectUtil.getFieldValue(methodInvocation, "method");

        for (Method method1 : methods) {
            if (isEqualTo(method1, method)) {
                return method1;
            }
        }
        return method;
    }

    /**
     * split the expression to keys
     *
     * @param value
     * @param expire
     * @return
     * @throws Exception
     */
    public CacheKey[] splitExpression(String value, int expire) throws Exception {
        String[] blocks = value.split("\\|");
        CacheKey[] cacheKeys = new CacheKey[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            CacheKey cacheKey = new CacheKey();
            cacheKey.unresolvedKey = blocks[i];
            cacheKey.expire = expire;
            cacheKeys[i] = cacheKey;
        }
        return cacheKeys;
    }

    /**
     * parse cache key
     * : separator of meaning
     * # SPEL expression
     *
     * @param key
     * @param method
     * @param args
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {
        LocalVariableTableParameterNameDiscoverer v = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = v.getParameterNames(method);

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }

        StringBuilder result = new StringBuilder();
        String[] combo = key.split(":");
        for (int i = 0; i < combo.length; i++) {
            if (StringUtils.isNotEmpty(combo[i])) {
                if (i > 0) {
                    result.append(":");
                }
                if (combo[i].startsWith("#")) {
                    result.append(parser.parseExpression(combo[i]).getValue(context, String.class));
                } else {
                    result.append(combo[i]);
                }
            }
        }
        return result.toString();
    }

    /**
     * compare methods
     *
     * @param m1
     * @param m2
     * @return
     */
    public boolean isEqualTo(Method m1, Method m2) {
        if ((m1.getClass() == m2.getClass())
                && (m1.getName().equals(m2.getName()))) {
            if (!m1.getReturnType().equals(m2.getReturnType())) {
                return false;
            }
            Class<?>[] params1 = m1.getParameterTypes();
            Class<?>[] params2 = m2.getParameterTypes();
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (params1[i] != params2[i])
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * CacheKey
     */
    private class CacheKey {
        private String unresolvedKey;
        private int expire;
    }
}
