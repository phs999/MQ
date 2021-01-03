package phs.activemq.helloworld;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息消费者
 * @author phs
 *
 */
public class BrowserQueue {

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
		QueueBrowser browser = session.createBrowser(queue);
		Enumeration enumeration = browser.getEnumeration();
		while(enumeration.hasMoreElements()) {
			TextMessage element = (TextMessage)enumeration.nextElement();
			System.out.println(element.getText());
		}
	}

}
