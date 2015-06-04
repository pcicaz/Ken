package com.sc.test.service;

import com.sc.test.bean.Organization;
import com.sc.test.bean.PageResult;
import com.sc.test.bean.User;
import com.sc.test.bean.UserInfo;
import com.sc.test.dao.IOrgDao;
import com.sc.test.dao.IUserDao;

public class UserServiceImpl implements IUserService {

    private IUserDao userDao;
    private IOrgDao orgDao;
    
    @Override
    public User login(User user) {
        return getUserDao().findUser(user);
    }

    @Override
    public User saveUser(User user) {

        Organization org = new Organization();
        org.setOrgCode("csczn");
        org.setOrgName("csczn");
        getOrgDao().save(org);
        UserInfo info = new UserInfo();
        info.setAddress("test address");
        user.setInfo(info);
        user.setOrg(org);
        getUserDao().save(user);
        return user;
    }

    @Override
    public void deleteUser(String username) {
        getUserDao().delete(username);
    }

    @Override
    public PageResult find(User user, int offset, int limit, String order) {
        return getUserDao().find(user, offset, limit, order);
    }

    @Override
    public void update(User user) {
        getUserDao().update(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return getUserDao().getUserByUsername(username);
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
