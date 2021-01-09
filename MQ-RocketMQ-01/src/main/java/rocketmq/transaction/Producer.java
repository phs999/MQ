package rocketmq.transaction;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;

/**
 * 消息发送者
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        TransactionMQProducer producer = new TransactionMQProducer("xxooProducerGroup");
        //设置nameserver地址
        producer.setNamesrvAddr("192.168.159.130:9876");

        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
               //执行本地事务

                /**
                 * 同步执行
                 * -----a----
                 * a 提交注册信息()  ;
                 * b 写入数据库();
                 * c 新用户() -> 发消息;
                 *
                 * ----b----
                 *
                 * 读取消息
                 * 拿到新用户的信息 发短信
                 *
                 */
                System.out.println("=====executeLocalTransaction");
                System.out.println("msg:"+new String(message.getBody()));
                System.out.println("msg TransactionId:"+message.getTransactionId());


                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                //broker 端回调，检测事务
                System.out.println("=====checkLocalTransaction");

                //事务执行成功
                return LocalTransactionState.COMMIT_MESSAGE;
                //等会 状态未知
                //return LocalTransactionState.UNKNOW;
                //回滚消息
                //return LocalTransactionState.ROLLBACK_MESSAGE;

            }
        });

        producer.start();

        TransactionSendResult sendResult = producer.sendMessageInTransaction(new Message("transtopic01", "事务消息测试".getBytes()), null);
        System.out.println("发送回执："+sendResult);

    }
}
