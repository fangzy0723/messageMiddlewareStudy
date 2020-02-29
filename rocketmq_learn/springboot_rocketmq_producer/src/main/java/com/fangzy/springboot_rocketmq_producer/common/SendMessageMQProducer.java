package com.fangzy.springboot_rocketmq_producer.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Slf4j
@Component
public class SendMessageMQProducer {

    @Value("${rocketmq.publicityProducer.topic}")
    public String topic;
    @Value("${rocketmq.publicityProducer.tag}")
    public String tag;
    @Autowired
    private DefaultMQProducer getMQProducer;

    /**
     * 发送消息
     */
    public String sendMessage(PublicityVo publicityVo) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        Message msg = new Message(topic, tag, JSON.toJSONBytes(publicityVo));
        //发送同步消息
        SendResult sendResult = getMQProducer.send(msg);
        log.debug("发送消息返回结果：{}", sendResult);
        return JSON.toJSONString(sendResult);
    }


}
