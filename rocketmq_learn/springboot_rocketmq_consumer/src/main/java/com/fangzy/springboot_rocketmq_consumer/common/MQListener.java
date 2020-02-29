package com.fangzy.springboot_rocketmq_consumer.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 集群模式消费消息
 */
@Slf4j
@Component
public class MQListener implements MessageListenerConcurrently {

    @Value("${rocketmq.paysuccessConsumer.namesrvAddr}")
    public String rocketmq_nameser;
    @Value("${rocketmq.paysuccessConsumer.topic}")
    public String rocketmq_topic;
    @Value("${rocketmq.paysuccessConsumer.tag}")
    public String rocketmq_tag;
    @Value("${rocketmq.paysuccessConsumer.groupName}")
    public String rocketmq_consumer_group;


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("消费消息开始：{}：{}", new String(list.get(0).getBody()), consumeConcurrentlyContext);
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        for (MessageExt e : list) {
            InvoiceResponse makeInvoiceVo = JSON.toJavaObject(JSON.parseObject(new String(e.getBody())), InvoiceResponse.class);
            //先判断当前消息是否被消费
            if (true/*校验消息是否被消费*/) {
                log.info("当前消息已被成功消费：{},{},{}", new String(e.getBody()), e.getMsgId(), consumeConcurrentlyContext);
                break;
            }
            try {
                int insertNum = 1;//调用消费消息的方法继续消息的消费;
                log.info("消息消费完成：{},{},{},入库状态:{}", new String(e.getBody()), e.getMsgId(), consumeConcurrentlyContext, insertNum > 0);
            } catch (Exception ex) {
                atomicBoolean.set(false);
                break;
            }
        }
        //CONSUME_SUCCESS 标识消费成功 ，根据重试策略进行重试消费，达到最大重试次数还没有消费成功将放到私信队列中
        return atomicBoolean.get() ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }

}
