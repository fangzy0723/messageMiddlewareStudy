package com.fangzy.springboot_rocketmq_producer.common;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Description rocketMQ生产者
 * @Author : fangzy
 * @Version 1.0
 */
@SpringBootConfiguration
public class MQProducerConfig {
    private static final Logger log = LoggerFactory.getLogger(MQProducerConfig.class);


    @Value("${rocketmq.publicityProducer.groupName}")
    public String publicityProducerGroup;
    @Value("${rocketmq.publicityProducer.namesrvAddr}")
    public String publicityProducerNameSrvAddr;
    @Value("${rocketmq.publicityProducer.instanceName}")
    public String publicityProducerInstance;
    @Value("${rocketmq.publicityProducer.maxMessageSize}")
    public int maxMessageSize;
    @Value("${rocketmq.publicityProducer.sendMsgTimeout}")
    public int sendMsgTimeout;
    @Value("${rocketmq.publicityProducer.retryTimesWhenSendFailed}")
    public int retryTimesWhenSendFailed;

    @Bean(name = "getMQProducer")
    public DefaultMQProducer getMQProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(publicityProducerGroup);
        producer.setNamesrvAddr(publicityProducerNameSrvAddr);
        producer.setInstanceName(publicityProducerInstance);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        producer.start();
        log.info("【生产者启动成功】");
        return producer;
    }
}
