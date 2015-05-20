package com.csczn.activemq.spring;

import com.csczn.activemq.test.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 04 25 2015 Steven
 */
public class SpringEntry {
    private static Log logger = LogFactory.getLog(SpringEntry.class);

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("123456");
        user.setEmail("111@ddd.com");
        user.setMobile("123456");

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml", "classpath:spring-comsumer.xml", "classpath:spring-producer.xml");
        SendService sendService = (SendService) context.getBean("sendService");

        sendService.send(user);
//        sendService.publish(user);
//        logger.debug("send successfully, please visit http://localhost:8161/admin to see it");
    }
}
