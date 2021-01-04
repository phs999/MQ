package phs.activemq.QueueRequestor;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

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
		//2.获取一个向ActiveMQ的连接
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//3.获取session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4.找目的地，获取destination，消费端也会从这个目的地取消息
		Queue queue = session.createQueue("user");

		//5.消息消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//6.从目的地获取消息
		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				System.out.println("接到一条消息。。。。");
				System.out.println("开始发送确认消息。。。");

				try {
					Destination replyTo = message.getJMSReplyTo();
					System.out.println("replyTo:" + replyTo);
					MessageProducer producer = session.createProducer(replyTo);
					producer.send(session.createTextMessage("xxxx..."));

				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
