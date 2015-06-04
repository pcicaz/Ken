package com.sc.test.dao;

import com.sc.test.bean.PageResult;
import com.sc.test.bean.User;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class UserDaoImpl extends MongodbDao<User> implements IUserDao {

    public UserDaoImpl(Datastore ds) {
        super(ds);
    }

    public User findUser(User user) {
        Query<User> query = this.createQuery();
        if (StringUtils.isNotEmpty(user.getUsername())) {
            query.field("username").equal(user.getUsername());
        }
        if (StringUtils.isNotEmpty(user.getPassword())) {
            query.field("password").equal(user.getPassword());
        }
        return this.findOne(query);
    }

    @Override
    public PageResult find(User user, int offset, int limit, String order) {
        PageResult result = new PageResult();
        Query<User> query = this.createQuery();
        if (StringUtils.isNotEmpty(user.getUsername())) {
            query.field("username").equal(user.getUsername());
        }
        if (StringUtils.isNotEmpty(user.getPassword())) {
            query.field("password").equal(user.getPassword());
        }
        long count = this.count(query);
        result.setTotal(count);
        if (StringUtils.isNotEmpty(order)) {
            query.order(order);
        }
        query.offset(offset).limit(limit);

        List<User> userList = this.find(query).asList();
        result.setResult(userList);
        return result;
    }

    @Override
    public void delete(String username) {
        Query<User> query = this.createQuery();
        query.criteria("username").equal(username);
        this.deleteByQuery(query);
    }

    @Override
    public User getUserByUsername(String username) {
        Query<User> query = this.createQuery();
        query.criteria("username").equal(username);
        return this.findOne(query);
    }

    @Override
    public void update(User user) {
        Query<User> query = this.createQuery();
        query.field("username").equal(user.getUsername());
        UpdateOperations<User> ops = this.createUpdateOperations();
        ops.set("password", user.getPassword());
        ops.set("gender", user.getGender());
        ops.set("birthday", user.getBirthday());
        ops.set("province", user.getProvince());
        ops.set("description", user.getDescription());
        this.update(query, ops);
    }
}
