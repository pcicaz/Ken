package com.csczn.test.morphia.bean;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexDirection;

@Indexes(@Index("name,-stars"))
@Entity(value = "users")
public class ObjectOne extends BaseBean {
    private static final long serialVersionUID = 115732484955098760L;
    
    // 建立索引users_name，升序，非唯一，不删除重复项
    @Indexed(value = IndexDirection.ASC, name = "users_name", unique = false, dropDups = false)
    private String name;
    
    private int stars;
    
    @Embedded
    private ObjectTwo two;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getStars() {
        return stars;
    }
    
    public void setStars(int stars) {
        this.stars = stars;
    }
    
    public ObjectTwo getTwo() {
        return two;
    }
    
    public void setTwo(ObjectTwo two) {
        this.two = two;
    }
    
}
