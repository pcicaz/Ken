package com.csczn.cache.service;

/**
 *
 * @date 05/06/2015
 * @author wangcheng
 */
public interface IMemcachedService {
    void set(String key, Object o, int expire) throws Exception;

    <T> T get(String key) throws Exception;

    void del(String key) throws Exception;
}
