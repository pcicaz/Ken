package com.scszn.service;

import com.csczn.model.User;
import com.csczn.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @date 05/06/2015
 * @author wangcheng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml",
        "classpath:spring-mybatis.xml", "classpath:spring-memcached.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class UserServiceTest {
    private static Log logger = LogFactory.getLog(UserServiceTest.class);
    @Resource
    private IUserService userService;

    @Test
    public void testGet() throws Exception {
        User user = userService.get("jack");
        logger.info(user);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> userList = userService.getAll();
        for (User user : userList) {
            logger.info(user);
        }
    }

    @Test
    public void testSave() throws Exception {
        User user = new User("jack", 123);
        userService.create(user);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("jack", 122);
        userService.update(user);
    }

    @Test
    public void testCache() throws Exception{
        User user = new User("tom", 22);
        userService.create(user);

        User tom = userService.get("tom");
        logger.info(tom);

        tom = userService.get("tom");//get again
        logger.info(tom);

        tom.setAge(23);
        userService.update(tom);

        tom = userService.get("tom");//get again and again
        logger.info(tom);


    }

}