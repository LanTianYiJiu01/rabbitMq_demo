package com.demo.rabbitMq.service;

import com.demo.rabbitMq.Constant.SysConstant;
import com.demo.rabbitMq.config.DelayedRabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 发生延时消息 到mq
 * @author lenovo
 */
@Component
public class SendMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 发送延时消息到mq
     * 根据：死信交换机   死信路由key
     * @param data  发送数据
     * @param delayTime  过期时间，单位毫秒
     */
    public void sendDelayMsgToMQ(String data , long delayTime){
        rabbitTemplate.convertAndSend(SysConstant.DEAD_LETTER_EXCHANGE, SysConstant.DEAD_LETTER_ROUTING, data, message -> {
            message.getMessageProperties().setExpiration(delayTime + "");
            return message;
        });
    }

    /**
     * 插件方式 实现延时消息
     * 没有 队列 先进后出 的问题。
     * @param msg
     * @param delayTime
     */
    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME, DelayedRabbitMQConfig.DELAYED_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }
}
