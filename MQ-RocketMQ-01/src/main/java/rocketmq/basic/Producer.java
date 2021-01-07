package rocketmq.basic;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 消息发送者
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("xxooProducerGroup");
        //设置nameserver地址
        producer.setNamesrvAddr("192.168.159.130:9876");
        producer.start();

        //topic 消息将要发送到的地址
        //body 消息中的具体数据
        Message msg1=new Message("mytopic","first message".getBytes());
        Message msg2=new Message("mytopic","second message".getBytes());
        Message msg3=new Message("mytopic","third message".getBytes());

        ArrayList<Message> list = new ArrayList<>();
        list.add(msg1);
        list.add(msg2);
        list.add(msg3);
        //同步消息发送 阻塞等待返回消息的发送结果
        //将消息组织到list中批量发送，可提高通过发送模式的效率
        SendResult result =producer.send(list);
        System.out.println("send result:"+result);


    }
}
