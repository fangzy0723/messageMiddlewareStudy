package com.fangzy.rocketmq_java_simple.simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("testProducerGroup");
        defaultMQProducer.setNamesrvAddr("192.168.152.139:9876");
        defaultMQProducer.setSendMsgTimeout(6000);
        defaultMQProducer.setInstanceName("producer");
        defaultMQProducer.start();



        Message message = new Message("TopicTest","tag1","hello world".getBytes());
        SendResult send = defaultMQProducer.send(message);
        System.out.println(send);

        defaultMQProducer.shutdown();


    }
}
