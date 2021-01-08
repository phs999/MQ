package rocketmq.basic;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;

/**
 * 消息发送者
 */
public class ProducerProperty {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("xxooProducerGroup");
        //设置nameserver地址
        producer.setNamesrvAddr("192.168.159.130:9876");
        producer.start();


        ArrayList<Message> list = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            Message msg=new Message("mytopic003",("message"+i).getBytes());
            msg.putUserProperty("age",String.valueOf(i));
            list.add(msg);
        }

        //同步消息发送 阻塞等待返回消息的发送结果
        //将消息组织到list中批量发送，可提高通过发送模式的效率
        SendResult result =producer.send(list);
        System.out.println("send result:"+result);


    }
}
