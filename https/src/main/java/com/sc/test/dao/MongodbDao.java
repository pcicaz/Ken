package com.sc.test.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class MongodbDao<T> extends BasicDAO<T, ObjectId> implements IMongodbDao<T> {

    public MongodbDao(Datastore ds) {
        super(ds);
    }
    
}
