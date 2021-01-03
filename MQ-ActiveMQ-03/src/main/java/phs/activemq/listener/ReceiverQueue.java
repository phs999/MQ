package phs.activemq.listener;

import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.reply.MessageSelectorCreator;

import bean.Girl;

/**
 * ��Ϣ������
 * @author phs
 *
 */
public class ReceiverQueue {
	
	public static void main(String[] args) throws Exception{
		//1.��ȡ���ӹ���
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin",
				"admin",
				"tcp://localhost:61616"
				);
		//1.1 ������εĳ־û�����
		ArrayList<String> trustList = new ArrayList<String>();
		trustList.add(Girl.class.getPackage().getName());
 		connectionFactory.setTrustedPackages(trustList);
		//2.��ȡһ����ActiveMQ������
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//3.��ȡsession
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.��Ŀ�ĵأ���ȡdestination�����Ѷ�Ҳ������Ŀ�ĵ�ȡ��Ϣ
		Queue queue = session.createQueue("user");
		
		//5.��Ϣ������
		MessageConsumer consumer = session.createConsumer(queue,"server=1");
		//6.��Ŀ�ĵػ�ȡ��Ϣ
		consumer.setMessageListener(new MyListener());
	}
	
}
