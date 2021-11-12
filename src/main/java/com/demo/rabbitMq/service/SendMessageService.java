package com.demo.rabbitMq.service;

import com.demo.rabbitMq.Constant.SysConstant;
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

}
