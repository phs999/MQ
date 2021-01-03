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
 * 消息发送者
 * @author phs
 *
 */
public class SenderQueue {
	
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
		Queue queue = session.createQueue("user");
		//Topic topic = session.createTopic("ff");
		
		//5.消息生产者
		MessageProducer producer = session.createProducer(queue);
		
		//序列化对象 implements Serializable
//		Girl girl = new Girl("Lucy", 18, 12);
//		ObjectMessage objectMessage = session.createObjectMessage(girl);
//		producer.send(objectMessage);
//		
//		//字节流 图 文件 小的
//		BytesMessage bytesMessage = session.createBytesMessage();
//		bytesMessage.writeUTF("hi~");
//		producer.send(bytesMessage);
		
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
