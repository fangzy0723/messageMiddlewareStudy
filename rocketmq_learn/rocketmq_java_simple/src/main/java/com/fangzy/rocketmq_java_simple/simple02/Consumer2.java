package com.fangzy.rocketmq_java_simple.simple02;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer2 {
    public static void main(String[] args)throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("g02");

        consumer.setNamesrvAddr("192.168.150.113:9876");

        consumer.subscribe("xxoo007", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {

                    System.out.println(new String(msg.getBody()));;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        /**
         * 发送消息的时候网络问题超时第一条准时到达，会发送第二条，这样的话会有两条一样的消息？

         */

        consumer.start();
        System.out.println("Consumer g02 start...");
    }
}
