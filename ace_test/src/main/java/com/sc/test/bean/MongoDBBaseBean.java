package com.sc.test.bean;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

public class MongoDBBaseBean implements Serializable {
    private static final long serialVersionUID = -3347247795131985409L;
    
    @Id
    private ObjectId id;
    
    @Version
    private long version;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
