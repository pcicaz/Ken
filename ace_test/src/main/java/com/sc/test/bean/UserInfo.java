package com.sc.test.bean;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 5460383422100571339L;
    
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
