package com.csczn.test.morphia.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.logging.Logger;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;

import com.csczn.test.morphia.bean.ObjectOne;

public class TestDao extends MongodbDao<ObjectOne> implements ITestDao
{
    protected TestDao(Datastore ds) {
        super(ds);
    }

    private static final Logger logger = MorphiaLoggerFactory.get(TestDao.class);
    
    
}
