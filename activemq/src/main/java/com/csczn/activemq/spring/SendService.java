package com.csczn.activemq.spring;

import com.csczn.activemq.test.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * 04 25 2015 Steven
 */
@Service("sendService")
public class SendService {
    private Log logger = LogFactory.getLog(SendService.class);
    @Resource
    private Queue testQueue;
    @Resource
    private Topic testTopic;
    @Resource
    private JmsTemplate jmsTemplate;
    @Resource
    private JmsTemplate transactedJmsTemplate;

    public void send(final User user) {
        logger.debug("send message ==>\n" + user);
//        jmsTemplate.setDeliveryPersistent(true);
//        jmsTemplate.convertAndSend(testQueue, user);
        transactedJmsTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object doInJms(Session session) throws JMSException {
//                jmsTemplate.setSessionTransacted(true);
                MessageProducer producer = session.createProducer(testQueue);
                Message msg = session.createTextMessage("test callback");
                producer.send(msg);
//                jmsTemplate.convertAndSend(testQueue, user);
//                if (1 == 1) {
//                    throw new JMSException("dddd");
//                }
                return null;
            }
        });
        logger.info("product message ==>\n" + user);

    }

    public void publish(final User user) {
        jmsTemplate.convertAndSend(testTopic, user);
    }
}