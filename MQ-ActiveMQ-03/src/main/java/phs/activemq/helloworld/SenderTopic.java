package phs.activemq.helloworld;

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
 * 消息发送者
 * @author phs
 *
 */
public class SenderTopic {

	public static void main(String[] args) throws Exception{
		//1.获取连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin",
				"admin",
				"tcp://localhost:61616"
		);
		//2.获取一个向ActiveMQ的连接
		Connection connection = connectionFactory.createConnection();
		//3.获取session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.找目的地，获取destination，消费端也会从这个目的地取消息
		Destination topic = session.createTopic("topic01");

		//5.消息生产者
		MessageProducer producer = session.createProducer(topic);
		//设置消息是否持久化 默认是持久化的
		//producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i < 100; i++) {
			Message textMessage = session.createTextMessage("hi");
			//6.向目的地写入消息
			producer.send(textMessage);
			//session.commit();

		}

		//7.关闭连接
		connection.close();


	}



}
