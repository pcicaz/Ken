package com.sc.test.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.sc.test.bean.User;

public class UserDaoImpl extends MongodbDao<User> implements IUserDao {

    public UserDaoImpl(Datastore ds) {
        super(ds);
    }

    public User findUser(String userName, String password){
        Query<User> query = this.createQuery();
        query.and(query.criteria("username").equal(userName), query.criteria("password").equal(password));
        return this.findOne(query);
    }
}
