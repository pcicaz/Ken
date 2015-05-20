package com.sc.test.dao;

import com.sc.test.bean.User;

public interface IUserDao extends IMongodbDao<User> {
    public User findUser(String userName, String password);
}
