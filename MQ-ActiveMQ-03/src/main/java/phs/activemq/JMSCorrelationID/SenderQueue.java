package phs.activemq.JMSCorrelationID;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;

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
		connection.start();
		//3.获取session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.找目的地，获取destination，消费端也会从这个目的地取消息
		Queue queue = session.createQueue("user");
		//Topic topic = session.createTopic("ff");

		//5.消息生产者
		MessageProducer producer = session.createProducer(queue);
		//设置消息是否持久化 默认是持久化的
		//producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		Message textMessage = session.createTextMessage("hi");
		String cid=UUID.randomUUID().toString();
		textMessage.setJMSCorrelationID(cid);
		textMessage.setStringProperty("type","C");


		producer.send(textMessage);
		System.out.println("P 消息发送完毕");

		//等待消息回复
		MessageConsumer consumer = session.createConsumer(queue,"JMSCorrelationID='"+cid+"' and type='P'");
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println("P 收到消息确认");
				try {
					//7.关闭连接
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});


	}

}
