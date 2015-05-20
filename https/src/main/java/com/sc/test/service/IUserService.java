package com.sc.test.service;

import com.sc.test.bean.User;

public interface IUserService {
    User login(String userName, String password);

    User saveUser(String userName, String password);
}
