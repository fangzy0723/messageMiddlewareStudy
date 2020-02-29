package com.fangzy.springboot_rocketmq_consumer.common;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Description rocketMQ消费者
 * @Author : fangzy
 * @Version 1.0
 */
@SpringBootConfiguration
public class MQConsumerConfig {
    private static final Logger log = LoggerFactory.getLogger(MQConsumerConfig.class);
    @Value("${rocketmq.paysuccessConsumer.namesrvAddr}")
    public String rocketmq_nameser;
    @Value("${rocketmq.paysuccessConsumer.topic}")
    public String rocketmq_topic;
    @Value("${rocketmq.paysuccessConsumer.tag}")
    public String rocketmq_tag;
    @Value("${rocketmq.paysuccessConsumer.groupName}")
    public String rocketmq_consumer_group;
    @Value("${rocketmq.paysuccessConsumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.paysuccessConsumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.paysuccessConsumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;
    @Autowired
    private MQListener mqListener;

    @Bean(name = "getRocketMQConsumer")
    public DefaultMQPushConsumer getRocketMQConsumer() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(rocketmq_consumer_group);
        defaultMQPushConsumer.setNamesrvAddr(rocketmq_nameser);
        defaultMQPushConsumer.subscribe(rocketmq_topic, rocketmq_tag);
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(mqListener);
        defaultMQPushConsumer.setConsumeThreadMin(consumeThreadMin);
        defaultMQPushConsumer.setConsumeThreadMax(consumeThreadMax);
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        defaultMQPushConsumer.start();
        log.info("*********************【消费者启动成功】*********************");
        return defaultMQPushConsumer;
    }
}
