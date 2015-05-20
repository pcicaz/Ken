package com.csczn.activemq.spring;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Apr 27 2015 Steven
 */
public class MyMessageConverter implements MessageConverter {
    private static Log log = LogFactory.getLog(MyMessageConverter.class);

    @SuppressWarnings("unchecked")
    @Override
    public Object fromMessage(Message msg) throws JMSException {
        if (msg instanceof ObjectMessage) {
            HashMap<String, byte[]> map = (HashMap<String, byte[]>) ((ObjectMessage) msg)
                    .getObjectProperty("Map");
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(map
                        .get("MSG_ID"));
                ObjectInputStream ois = new ObjectInputStream(bis);
                Object o = ois.readObject();
                ois.close();
                bis.close();
                return o;
            } catch (IOException e) {
                log.error("failed to read object message: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                log.error("failed to read object message: " + e.getMessage());
            }
        } else {
            throw new JMSException("Message: [" + msg + "] is not a Map !");
        }
        return null;
    }

    public Message toMessage(Object obj, Session session) throws JMSException,
            MessageConversionException {
        ActiveMQObjectMessage o = (ActiveMQObjectMessage) session
                .createObjectMessage();
        Map<String, byte[]> map = new HashMap<String, byte[]>();
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            map.put("MSG_ID", bos.toByteArray());
            oos.close();
            bos.close();
        } catch (IOException e) {
            log.error("failed to write object message: " + e.getMessage());
        }
        o.setObjectProperty("Map", map);
        return o;
    }
}


