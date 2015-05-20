package com.csczn.activemq.spring;

import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.csczn.activemq.test.User;

/**
 * Apr 27 2015 Steven
 */
public class MyQueueConsumer {
    private static Log logger = LogFactory.getLog(MyQueueConsumer.class);

    public void onMessage(User user) {
        logger.info("receive message <==\n" + user);
    }
    public void onMessage(Message message) {
        logger.info("receive message <==\n" + message);
    }
    public void onMessage(String message) {
        logger.info("receive message <==\n" + message);
    }
}
