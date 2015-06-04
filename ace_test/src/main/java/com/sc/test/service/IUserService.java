package com.sc.test.service;

import com.sc.test.bean.PageResult;
import com.sc.test.bean.User;

public interface IUserService {
    User login(User user);

    User saveUser(User user);

    User getUserByUsername(String username);

    void deleteUser(String username);

    PageResult find(User user, int offset, int limit, String order);

    void update(User user);
}
