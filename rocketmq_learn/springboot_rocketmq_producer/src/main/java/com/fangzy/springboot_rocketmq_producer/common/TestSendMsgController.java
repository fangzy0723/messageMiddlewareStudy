package com.fangzy.springboot_rocketmq_producer.common;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSendMsgController {

    @Autowired
    private SendMessageMQProducer sendMessageMQProducer;

    @GetMapping("/")
    public void testSendMsg() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String result = sendMessageMQProducer.sendMessage(PublicityVo.builder().id(1).name("aaa").build());
        System.out.println("消息发送返回结果："+result);
    }
}
