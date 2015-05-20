package com.csczn.test.morphia.bean;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;

public class BaseBean implements Serializable,Cloneable {
    private static final long serialVersionUID = 8822550228924373948L;
    
    
    
    @Id
    @Property("id")
    private ObjectId mid;

    @Version
    private long version;
    
    public ObjectId getMid()
    {
        return mid;
    }
    
    public void setMid(ObjectId mid)
    {
        this.mid = mid;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
    
    public Object clone(){
        BaseBean o = null;
        try{
            o = (BaseBean)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
}
