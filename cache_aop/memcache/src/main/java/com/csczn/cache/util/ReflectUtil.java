package com.csczn.cache.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;

/**
 *
 * @date 05/06/2015
 * @author wangcheng
 */
public class ReflectUtil {
    private static Log logger = LogFactory.getLog(ReflectUtil.class);

    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                //do nothing
            }
        }
        return field;
    }

    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
        Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
