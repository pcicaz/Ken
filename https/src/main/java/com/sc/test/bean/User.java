package com.sc.test.bean;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity(value="sc_users")
public class User extends MongoDBBaseBean {
    private static final long serialVersionUID = 6559900232058267592L;
    
    private String username;
    private String password;
    
    @Embedded
    private UserInfo info;
    
    @Reference(value="org_id", lazy=true)
    private Organization org;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }
}
