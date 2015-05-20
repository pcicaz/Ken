package com.csczn.service;

import com.csczn.cache.annotation.MemCache;
import com.csczn.dao.UserMapper;
import com.csczn.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @date 05/06/2015
 * @author wangcheng
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @MemCache(expression = "user:userList", mode = MemCache.Mode.EVICT)
    public void create(User user) {
        userMapper.create(user);
    }

    @Override
    @MemCache(expression = "user:#name", mode = MemCache.Mode.EVICT)
    public void delete(String name) {
        userMapper.delete(name);
    }

    @Override
    @MemCache(expression = "user:#name", expire = 7200)
    public User get(String name) {
        return userMapper.retrieveByName(name);
    }

    @Override
    @MemCache(expression = "user:userList")
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    @MemCache(expression = "user:#user.name|user:userList", mode = MemCache.Mode.EVICT)
    public void update(User user) {
        userMapper.updateByName(user);
    }
}
