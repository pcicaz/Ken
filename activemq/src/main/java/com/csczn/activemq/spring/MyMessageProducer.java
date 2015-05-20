package com.csczn.activemq.spring;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * Apr 27 2015 Steven
 */
public class MyMessageProducer {
    private static Log log = LogFactory.getLog(MyMessageProducer.class);
    @Resource
    private JmsTemplate jmsTemplate;
    @Resource
    private Queue testQueue;

    public void send(final Message message) {
        jmsTemplate.execute(new ProducerCallback<Object>() {
            @Override
            public Object doInJms(Session session, MessageProducer producer) throws JMSException {
                jmsTemplate.convertAndSend(testQueue, message);
//                session.rollback();
                return null;
            }
        });
        log.info("product message ==>\n" + message);
    }
}
