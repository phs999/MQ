package phs.activemq.listener;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ��Ϣ������
 * @author phs
 *
 */
public class SenderTopic {
	
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
		Destination topic = session.createTopic("topic01");
		
		//5.��Ϣ������
		MessageProducer producer = session.createProducer(topic);
		//������Ϣ�Ƿ�־û� Ĭ���ǳ־û���
		//producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i < 100; i++) {
			Message textMessage = session.createTextMessage("hi");
			//6.��Ŀ�ĵ�д����Ϣ
			producer.send(textMessage);
			//session.commit();
			
		}
		
		//7.�ر�����
		connection.close();
		
		
	}
	
	
	
}
