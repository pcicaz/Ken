package com.csczn.activemq.spring;

import com.csczn.activemq.test.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Apr 27 2015 Steven
 */
public class MyTopicSubscriber {
    private static Log logger = LogFactory.getLog(MyTopicSubscriber.class);

    public void onMessage(User user) {
        logger.info("receive topic message <==\n" + user);
    }
}
