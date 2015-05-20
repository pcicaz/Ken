package com.csczn.test.morphia.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class MongodbDao<T> extends BasicDAO<T, ObjectId> implements IMongodbDao<T> {

    protected MongodbDao(Datastore ds) {
        super(ds);
    }
    
}
