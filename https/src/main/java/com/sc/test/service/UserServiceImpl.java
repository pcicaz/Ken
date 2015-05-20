package com.sc.test.service;

import com.sc.test.bean.Organization;
import com.sc.test.bean.User;
import com.sc.test.bean.UserInfo;
import com.sc.test.dao.IOrgDao;
import com.sc.test.dao.IUserDao;

public class UserServiceImpl implements IUserService {

    private IUserDao userDao;
    private IOrgDao orgDao;
    
    @Override
    public User login(String userName, String password) {
        return getUserDao().findUser(userName, password);
    }

    @Override
    public User saveUser(String userName, String password) {

        Organization org = new Organization();
        org.setOrgCode("csczn");
        org.setOrgName("csczn");
        getOrgDao().save(org);
        UserInfo info = new UserInfo();
        info.setAddress("test address");
        User user = new User();
        user.setInfo(info);
        user.setOrg(org);
        user.setPassword("test");
        user.setUsername("test");
        getUserDao().save(user);
        return user;
    }
    

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public IOrgDao getOrgDao() {
        return orgDao;
    }

    public void setOrgDao(IOrgDao orgDao) {
        this.orgDao = orgDao;
    }
}
