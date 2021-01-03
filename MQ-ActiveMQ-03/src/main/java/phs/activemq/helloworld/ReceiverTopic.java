package phs.activemq.helloworld;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息消费者
 * @author phs
 *
 */
public class ReceiverTopic {

	public static void main(String[] args) throws Exception{
		//1.获取连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin",
				"admin",
				"tcp://localhost:61616"
		);
		//2.获取一个向ActiveMQ的连接
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//3.获取session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.找目的地，获取destination，消费端也会从这个目的地取消息
		Destination topic = session.createTopic("topic01");

		//5.消息消费者
		MessageConsumer consumer = session.createConsumer(topic);
		//6.从目的地获取消息
		while(true) {
			Message message = consumer.receive();
			System.out.println(message.toString());
			//message.acknowledge();
			//session.commit();
		}
	}

}
