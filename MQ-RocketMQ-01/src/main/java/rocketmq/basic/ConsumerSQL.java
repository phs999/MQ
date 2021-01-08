package rocketmq.basic;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class ConsumerSQL {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxooConsumerGroup");

        consumer.setNamesrvAddr("192.168.159.130:9876");

        MessageSelector selector = MessageSelector.bySql("age <=20 and age>=4");

        //topic
        //selector 过滤器 *表示不过滤MessageSelector
        //tag selector 在一个group中的消费者，都不能随便变，要保持统一
        consumer.subscribe("mytopic003",selector);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                for (MessageExt msg: list) {
                    System.out.println(new String(msg.getBody()));
                }
                //默认情况下这条消息只会被一个consumer消费到点对点
                //massage 的状态修改
                //ack
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        /*消息消费模式 默认为集群消费模式 CLUSTERING
        消息消费模式由消费者来决定，可以由消费者设置MessageModel来决定消息模式。
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.setMessageModel(MessageModel.CLUSTERING);*/

        consumer.start();
    }
}
