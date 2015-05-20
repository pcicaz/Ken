package com.csczn.test.morphia.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

public interface IMongodbDao<T> extends DAO<T, ObjectId> {
    
}
