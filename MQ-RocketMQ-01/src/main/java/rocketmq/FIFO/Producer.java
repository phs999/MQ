package rocketmq.FIFO;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * 消息发送者
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("xxooProducerGroup");
        //设置nameserver地址
        producer.setNamesrvAddr("192.168.159.130:9876");

        producer.start();

        for (int i = 0; i < 20; i++) {
            Message message = new Message("fifo",("保证消息顺序发送顺序消费"+i).getBytes());

            //producer.send(message,new SelectMessageQueueByHash(),1);
       /*public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
        int value = arg.hashCode();
        if (value < 0) {
            value = Math.abs(value);
        }

        value %= mqs.size();
        return (MessageQueue)mqs.get(value);
    }*/
            SendResult sendResult = producer.send(message,
                    //queue选择器，选择向topic的指定queue中去写消息
                    new MessageQueueSelector() {
                        @Override
                        //手动选择一个queue 此处为选择策略
                        public MessageQueue select(
                                //当前topic中包含的所有queue
                                List<MessageQueue> list,
                                //要发送的消息
                                Message message,
                                //对应到 send()中的args
                                Object args) {

                            //选择好的queue 向固定的queue中发送消息
                            return list.get((Integer) args);
                        }
                    }, 1, 2000
            );
            System.out.println("发送回执："+sendResult);
        }


    }
}
