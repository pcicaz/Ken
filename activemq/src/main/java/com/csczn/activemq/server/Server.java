package com.csczn.activemq.server;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.jdbc.JDBCPersistenceAdapter;
import org.apache.activemq.store.jdbc.adapter.MySqlJDBCAdapter;
import org.apache.activemq.store.kahadb.KahaDBStore;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
import javax.sql.DataSource;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * Apr 27 2015 Steven
 */
public class Server {
    private static Log logger = LogFactory.getLog(Server.class);
    public static String jmxDomain = "jms-broker";
    public static int connectorPort = 2011;
    public static String connectorPath = "/jmxrmi";

    public static String TEST_QUEUE = "test.queue";
    private BrokerService brokerService;

    public BrokerService createBrokerService() throws Exception {
        if (brokerService == null) {
            brokerService = createBroker();
        }
        return brokerService;
    }

    public BrokerService createBroker() throws Exception {
        BrokerService broker = new BrokerService();
        PersistenceAdapter adapter = createJDBCPersistenceAdapter();
//        PersistenceAdapter adapter = createKahaDBStore();
        broker.setPersistenceAdapter(adapter);
        broker.addConnector("tcp://localhost:61616");
        broker.setPersistent(true);
        return broker;
    }

    public JDBCPersistenceAdapter createJDBCPersistenceAdapter() throws Exception {
        JDBCPersistenceAdapter adapter = new JDBCPersistenceAdapter();
        DataSource dataSource = createDataSource();
        adapter.setDataSource(dataSource);
        adapter.setAdapter(new MySqlJDBCAdapter());
        adapter.setDataDirectory("data");
        return adapter;
    }

    public KahaDBStore createKahaDBStore() throws Exception {

        File dataFilterDir = new File("activemq/kahadb");
        KahaDBStore kaha = new KahaDBStore();
        kaha.setDirectory(dataFilterDir);
        // use a bigger journal file
        kaha.setJournalMaxFileLength(1024 * 100);
        // small batch means more frequent and smaller writes
        kaha.setIndexWriteBatchSize(100);
        // do the index write in a separate thread
        kaha.setEnableIndexWriteAsync(true);
        return kaha;
    }

    public DataSource createDataSource() throws Exception {
        Properties properties = new Properties();
        properties.put("driverClassName", "com.mysql.jdbc.Driver");
        properties.put("url", "jdbc:mysql://localhost:3306/activemq?useUnicode=true&amp;characterEncoding=utf-8");
        properties.put("username", "root");
        properties.put("password", "pass");
        return BasicDataSourceFactory.createDataSource(properties);
    }

    /**
     * 启动activeMQ服务
     */
    public static void main(String[] args) throws Exception {
        // java代码调用activemq相关的类来构造并启动brokerService
        BrokerService broker = new Server().createBrokerService();

        // 以下是ManagementContext的配置，从这个容器中可以取得消息队列中未执行的消息数、消费者数、出队数等等
        // 设置ManagementContext
        ManagementContext context = broker.getManagementContext();
        context.setConnectorPort(connectorPort);
        context.setJmxDomainName(jmxDomain);
        context.setConnectorPath(connectorPath);
        broker.start();
        produce();
        System.exit(1);
    }

    public static void produce() throws Exception {
        long start = new Date().getTime();
        logger.debug("start time: " + start);
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.setClientID("client1");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//true 开启事务 指定ACK_Mode签收确认模式为自动确认
        Destination destination = session.createQueue(TEST_QUEUE);
        MessageProducer producer = session.createProducer(destination);

        String text = "test message From: " + Thread.currentThread().getName() + " : ";
        TextMessage message = session.createTextMessage(text);
        logger.debug("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
//        producer.setTimeToLive(5000);//5s
        long beforeSend = new Date().getTime();
        logger.debug("beforeSend time: " + beforeSend);
        producer.send(message);
        long afterSend = new Date().getTime();
        logger.debug("after time: " + afterSend);
//        session.commit();//提交事务
//        session.rollback();//事务回滚
        session.close();
        connection.close();
        long end = new Date().getTime();
        logger.debug("end time: " + end);
        logger.debug("cost: " + (end - start));
    }

}
