package phs.activemq.listener;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import bean.Girl;

/**
 * ��Ϣ������
 * @author phs
 *
 */
public class SenderQueue {
	
	public static void main(String[] args) throws Exception{
		//1.��ȡ���ӹ���
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin",
				"admin",
				"tcp://localhost:61616"
				);
		//2.��ȡһ����ActiveMQ������
		Connection connection = connectionFactory.createConnection();
		//3.��ȡsession
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.��Ŀ�ĵأ���ȡdestination�����Ѷ�Ҳ������Ŀ�ĵ�ȡ��Ϣ
		Queue queue = session.createQueue("user");
		//Topic topic = session.createTopic("ff");
		
		//5.��Ϣ������
		MessageProducer producer = session.createProducer(queue);
		
		//���л����� implements Serializable
//		Girl girl = new Girl("Lucy", 18, 12);
//		ObjectMessage objectMessage = session.createObjectMessage(girl);
//		producer.send(objectMessage);
//		
//		//�ֽ��� ͼ �ļ� С��
//		BytesMessage bytesMessage = session.createBytesMessage();
//		bytesMessage.writeUTF("hi~");
//		producer.send(bytesMessage);
		
		//map
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("name", "zs");
		mapMessage.setInt("age", 34);
		
		mapMessage.setLongProperty("server", 1);
		producer.send(mapMessage);
		//7.�ر�����
		connection.close();
		
		
	}
	
	
	
}
