package com.fangzy.rocketmq_java_simple.simple02;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class Producer2 {

    public static void main(String[] args)throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("xoxogp1");

        producer.setNamesrvAddr("192.168.150.113:9876");
        // 回调
        producer.start();
        //几次
        producer.setRetryTimesWhenSendFailed(2);
        producer.send(new Message("topic01","tag1","asdas".getBytes()));
        producer.setRetryAnotherBrokerWhenNotStoreOK(true);

        //	producer.shutdown();
        System.out.println("已经停机");

    }
}
