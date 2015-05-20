package com.csczn.test.morphia.bean;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ObjectTwo implements Serializable
{
    private static final long serialVersionUID = -3870123989192115711L;
    
    private String street;
    
    private String city;
    
    private String postCode;
    
    private String country;
    
    public String getStreet()
    {
        return street;
    }
    
    public void setStreet(String street)
    {
        this.street = street;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getPostCode()
    {
        return postCode;
    }
    
    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
}
