package com.sc.test.dao;

import com.sc.test.bean.PageResult;
import com.sc.test.bean.User;

public interface IUserDao extends IMongodbDao<User> {
    User findUser(User user);

    PageResult find(User user, int offset, int limit, String order);

    void delete(String username);

    User getUserByUsername(String username);

    void update(User user);
}
