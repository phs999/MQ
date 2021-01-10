package rocketmq.FIFO;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 保证消息的消费顺序
 * - 同一topic
 *
 * - 同一个QUEUE（一个topic默认有4个queue）
 *
 * - 发消息的时候一个线程去发送消息
 *
 * - 消费的时候 一个线程 消费一个queue里的消息或者使用MessageListenerOrderly
 *
 * - 多个queue 只能保证单个queue里的顺序
 */
public class Consumer2 {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxooConsumerGroup");

        consumer.setNamesrvAddr("192.168.159.130:9876");

        //topic
        //selector 过滤器 *表示不过滤MessageSelector
        //tag selector 在一个group中的消费者，都不能随便变，要保持统一
        consumer.subscribe("fifo","*");

        //最大开启消费线程数
        consumer.setConsumeThreadMax(1);
        //最小开启消费线程数
        consumer.setConsumeThreadMin(1);

        consumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg: list) {
                    System.out.println(new String(msg.getBody())+" Thread :"+Thread.currentThread().getName());
                }
                //默认情况下这条消息只会被一个consumer消费到点对点
                //massage 的状态修改
                //ack
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });



        consumer.start();
    }
}
