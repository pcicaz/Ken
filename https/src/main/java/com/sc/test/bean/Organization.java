package com.sc.test.bean;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

@Entity(value="sc_orgs")
public class Organization extends MongoDBBaseBean {
    private static final long serialVersionUID = 7611756958164124766L;
    
    @Property("name")
    private String orgName;

    @Property("code")
    private String orgCode;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
