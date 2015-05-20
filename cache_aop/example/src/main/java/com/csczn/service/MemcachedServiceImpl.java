package com.csczn.service;

import com.csczn.cache.service.IMemcachedService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeoutException;

/**
 *
 * @date 05/06/2015
 * @author wangcheng
 */
@Service("memcachedService")
public class MemcachedServiceImpl implements IMemcachedService {
    private static Log logger = LogFactory.getLog(MemcachedServiceImpl.class);
    @Resource
    private MemcachedClient memcachedClient;

    @Override
    public void set(String key, Object o, int expire) throws InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.set(key, expire, o);
    }

    @Override
    public <T> T get(String key) throws InterruptedException, MemcachedException, TimeoutException {
        return memcachedClient.get(key);
    }

    public void del(String key) throws InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.delete(key);
    }
}
