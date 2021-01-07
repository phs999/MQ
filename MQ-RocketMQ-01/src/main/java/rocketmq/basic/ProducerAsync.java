package rocketmq.basic;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;

/**
 * 异步 消息发送者
 */
public class ProducerAsync {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer producer = new DefaultMQProducer("xxooProducerGroup");
        //设置nameserver地址
        producer.setNamesrvAddr("192.168.159.130:9876");
        producer.start();

        //topic 消息将要发送到的地址
        //body 消息中的具体数据
        Message msg=new Message("mytopic","first message".getBytes());

       // producer.setRetryTimesWhenSendAsyncFailed(3);
        //异步可靠消息
        //不会阻塞，异步等待broker的确认 采用事件监听的方式接受broker返回的确认
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("send result:"+sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                //如果发生异常， case异常情况 尝试重投 或者调整业务逻辑
                throwable.printStackTrace();
                System.out.println("发送异常");
            }
        });

    }
}
