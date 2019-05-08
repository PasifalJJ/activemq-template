package com.jsc;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {
    private static final String USERNAME= ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
    private static final String PASSWORD= ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
    private static final String BROKEURL= ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址

    public static void main(String[] args) {
        System.out.println("USERNAME = " + USERNAME);
        System.out.println("PASSWORD = " + PASSWORD);
        System.out.println("BROKEURL = " + BROKEURL);

        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

        try {
            //创建连接工厂
            ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
            //创建连接
            connection = activeMQConnectionFactory.createConnection();
            //开启连接
            connection.start();
            //创建会话session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建目标点
            Destination destination = session.createTopic("雪中粉丝");
            //创建订阅生产者,生产内容指向destination
            producer = session.createProducer(destination);
            //创建发送信息
            TextMessage msg = session.createTextMessage("姜泥");
            producer.send(destination,msg);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                producer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
