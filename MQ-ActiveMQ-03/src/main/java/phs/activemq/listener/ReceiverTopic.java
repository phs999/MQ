package phs.activemq.listener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ��Ϣ������
 * @author phs
 *
 */
public class ReceiverTopic {
	
	public static void main(String[] args) throws Exception{
		//1.��ȡ���ӹ���
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin",
				"admin",
				"tcp://localhost:61616"
				);
		//2.��ȡһ����ActiveMQ������
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//3.��ȡsession
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.��Ŀ�ĵأ���ȡdestination�����Ѷ�Ҳ������Ŀ�ĵ�ȡ��Ϣ
		Destination topic = session.createTopic("topic01");
		
		//5.��Ϣ������
		MessageConsumer consumer = session.createConsumer(topic);
		//6.��Ŀ�ĵػ�ȡ��Ϣ
		while(true) {
			Message message = consumer.receive();
			System.out.println(message.toString());
			//message.acknowledge();
			//session.commit();
		}
	}
	
}
