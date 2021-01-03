package phs.activemqspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class SenderService {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String des, String content) {
        jmsMessagingTemplate.convertAndSend(des,content);

    }
    public void send2(String des, String content) {
        jmsTemplate.send(des, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("q23423");
                textMessage.setStringProperty("name","zd");
                return textMessage;
            }
        });
    }

    public void send3(String des, String content) throws JMSException {
        ConnectionFactory connectionFactory=jmsTemplate.getConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("user");
        //5.消息生产者
        MessageProducer producer = session.createProducer(queue);
        //Topic topic = session.createTopic("ff");
        //map
        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("name", "zs");
        mapMessage.setInt("age", 34);

        mapMessage.setLongProperty("server", 1);
        producer.send(mapMessage);
        //7.关闭连接
        connection.close();

    }
}
