package com.sc.test.dao;

import org.mongodb.morphia.Datastore;

import com.sc.test.bean.Organization;

public class OrgDaoImpl extends MongodbDao<Organization> implements IOrgDao {

    public OrgDaoImpl(Datastore ds) {
        super(ds);
    }
    
}
