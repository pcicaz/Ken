package com.csczn.activemq.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Apr 27 2015 Steven
 */
public class MyMessageListener implements MessageListener {
    private static Log logger = LogFactory.getLog(MyMessageListener.class);
    @Override
    public void onMessage(Message message) {
        logger.info("receive <==\n" + message);
    }
}
