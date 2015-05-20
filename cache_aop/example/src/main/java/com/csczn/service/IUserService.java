package com.csczn.service;

import com.csczn.model.User;

import java.util.List;

/**
 *
 * @date 05/06/2015
 * @author wangcheng
 */
public interface IUserService {
    void create(User user);

    void delete(String name);

    User get(String name);

    List<User> getAll();

    void update(User user);
}
