package com.csczn.activemq.test;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 04 25 2015 Steven
 */
public class Consumer {
    private static Log logger = LogFactory.getLog(Consumer.class);

    public static void main(String[] args) throws Exception {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        Connection connection = connectionFactory.createConnection();

        connection.start();
//        RedeliveryPolicy policy = ((ActiveMQConnection) connection).getRedeliveryPolicy();
        final Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);//指定ACK_Mode签收确认模式为自动确认
        Destination destination = session.createQueue("test.queue?consumer.exclusive=true");//创建消息目标(点对点模型队列)
//        Destination destination = session.createQueue("test.queue?consumer.priority=10");//创建消息目标(点对点模型队列)

//        Destination destination = session.createQueue("test.queue");//创建消息目标(点对点模型队列)
//Destination destination = session.createTopic("test.topic");//创建消息目标(订阅主题)
        MessageConsumer consumer = session.createConsumer(destination);//创建消息生产者

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {//判断消息类型是否为文本消息
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        message.acknowledge();//消息签收
//                            session.rollback();
                        session.commit();
                        String text = textMessage.getText();
                        logger.debug("Received message: " + text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.debug("Received message: " + message);
                }
            }
        });

// Clean up
//        session.close();//关闭会话
//        connection.close();//关闭连接
    }
}
