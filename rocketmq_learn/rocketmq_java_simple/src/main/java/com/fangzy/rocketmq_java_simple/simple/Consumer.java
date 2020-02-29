package com.fangzy.rocketmq_java_simple.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class Consumer {
    public static void main(String[] args)throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxoocsm");

        consumer.setNamesrvAddr("192.168.152.139:9876");



        // 每个consumer 关注一个topic

        // topic 关注的消息的地址
        // 过滤器 * 表示不过滤
        consumer.subscribe("myTopic002", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {

                    System.out.println(new String(msg.getBody()));;
                }
                // 默认情况下 这条消息只会被 一个consumer 消费到 点对点
                // message 状态修改
                // ack
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });


        // 讲道理 冲突，首先，别这么干
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.start();

        // 集群 -> 一组consumer
        // 广播

        System.out.println("Consumer 02 start...");
    }
}
