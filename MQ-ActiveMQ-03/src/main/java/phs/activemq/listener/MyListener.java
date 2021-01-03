package phs.activemq.listener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import bean.Girl;

public class MyListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)message;
			try {
				System.out.println(textMessage.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (message instanceof ObjectMessage) {
			ObjectMessage objectMessage=(ObjectMessage)message;
			try {
				Girl girl =(Girl) objectMessage.getObject();
				System.out.println(girl.getName());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (message instanceof BytesMessage) {
			BytesMessage bytesMessage=(BytesMessage)message;
			 FileOutputStream out = null;
             try {
                 out = new FileOutputStream("d:/aa.txt");
             } catch (FileNotFoundException e2) {
                 e2.printStackTrace();
             }
             byte[] by = new byte[1024];
             int len = 0 ;
             try {
                 while((len = bytesMessage.readBytes(by))!= -1){
                     out.write(by,0,len);
                 }
             } catch (Exception e1) {
                 e1.printStackTrace();
             }
			/*
			 * try { System.out.println(bytesMessage.readUTF()); } catch (JMSException e) {
			 * // TODO Auto-generated catch block e.printStackTrace(); }
			 */
		}else if (message instanceof MapMessage) {
			MapMessage mapMessage = (MapMessage)message;
			try {
				System.out.println(mapMessage.getString("name"));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		
	}

}
