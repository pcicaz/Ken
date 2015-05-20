package com.csczn.activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.Date;

/**
 * 04 25 2015 Steven
 */
public class Producer {
    private static Log logger = LogFactory.getLog(Producer.class);

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:(tcp://192.168.1.253:61616,tcp://192.168.1.253:61617,tcp://192.168.1.253:61618)?initialReconnectDelay=100");

//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.setClientID("client1");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//true 开启事务 指定ACK_Mode签收确认模式为自动确认

        Destination destination = session.createQueue("test.queue");
//        Destination destination = session.createTopic("test.topic");

        MessageProducer producer = session.createProducer(destination);
//        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//指定传输模式-非持久性消息

        String text = "Hello world! From: " + Thread.currentThread().getName() + " : ";
        TextMessage message = session.createTextMessage(text);
        System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
        producer.setTimeToLive(5000);//5s
        producer.send(message);
        long afterSend = new Date().getTime();
        logger.debug("after send: " + afterSend);
//        session.commit();//提交事务
//        session.rollback();//事务回滚
        session.close();
        connection.close();
//        Thread.sleep(100);
//        closeMQ();
        long closeTime = new Date().getTime();
        logger.debug("close mq: " + closeTime);
        logger.debug("cost: " + (closeTime - afterSend));
    }


    public static void closeMQ() throws IOException {
        String[] cmd = {"/bin/sh", "-c", "kill $(lsof -i :61616|tail -1|awk '{print $2}')"};
        Runtime.getRuntime().exec(cmd);
    }
}
