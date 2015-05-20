package com.csczn.dao;

import com.csczn.model.User;

import java.util.List;

public interface UserMapper {
    void create(User user);

    void delete(String name);

    User retrieveByName(String name);

    void updateByName(User user);

    List<User> getAll();
}