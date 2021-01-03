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
 * 消息消费者
 * @author phs
 *
 */
public class ReceiverQueue {
	
	public static void main(String[] args) throws Exception{
		//1.获取连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin",
				"admin",
				"tcp://localhost:61616"
				);
		//1.1 添加信任的持久化类型
		ArrayList<String> trustList = new ArrayList<String>();
		trustList.add(Girl.class.getPackage().getName());
 		connectionFactory.setTrustedPackages(trustList);
		//2.获取一个向ActiveMQ的连接
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//3.获取session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.找目的地，获取destination，消费端也会从这个目的地取消息
		Queue queue = session.createQueue("user");
		
		//5.消息消费者
		MessageConsumer consumer = session.createConsumer(queue,"server=1");
		//6.从目的地获取消息
		consumer.setMessageListener(new MyListener());
	}
	
}
