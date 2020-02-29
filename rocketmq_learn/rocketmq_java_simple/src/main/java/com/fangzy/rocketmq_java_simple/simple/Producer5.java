package com.fangzy.rocketmq_java_simple.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

public class Producer5 {
    public static void main(String[] args)throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("xoxogp");

        // 设置nameserver地址
        producer.setNamesrvAddr("192.168.152.139:9876");
        producer.start();

        // tag 是用来过滤消息，消息分组


        for (int i = 1; i <= 100; i++) {

            Message message = new Message("myTopic003", "TAG-B","KEY-xx",("xxooxx:" + i ).getBytes());
            message.putUserProperty("age", String.valueOf(i));
            producer.send(message);
        }



        // 单向消息
        // p 网络不确定

        producer.shutdown();
        System.out.println("已经停机");

    }
}
