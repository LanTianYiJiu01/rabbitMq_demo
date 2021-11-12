package com.demo.rabbitMq.config;

import com.demo.rabbitMq.Constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时消息队列配置
 * 利用 死信队列 将过期队列消息转到 新的消息队列中 实现的
 * @author lenovo
 */
@Configuration
@Slf4j
public class TimingQueueConfig {

    @Bean
    DirectExchange deadLetterExchange(){
        log.debug("创建 -死信队列转发的交换机-名称为：{}",SysConstant.DEAD_LETTER_EXCHANGE);
        return new DirectExchange(SysConstant.DEAD_LETTER_EXCHANGE);
    }

    /**
     * x-message-ttl为过期时间，该队列所有消息的过期时间都为我们配置的这个值，单位为毫秒
     * x-dead-letter-exchange是指过期消息重新转发到指定交换机
     * x-dead-letter-routing-key是该交换机上绑定的routing-key，将通过配置的routing-key分配对应的队列
     * @return
     */
    @Bean
    public Queue deadLetterQueue(){
        log.debug("将过死信消息队列：{}的消息通过配置的routing-key:{},重新转发到指定交换机：{},进而重新发生消息队列：{}"
                ,SysConstant.DEAD_LETTER_QUEUE,SysConstant.RESEND_MESSAGE_ROUTING,
                SysConstant.RESEND_MESSAGE_EXCHANGE,SysConstant.RESEND_MESSAGE_EXCHANGE);
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", SysConstant.RESEND_MESSAGE_EXCHANGE);
        map.put("x-dead-letter-routing-key", SysConstant.RESEND_MESSAGE_ROUTING);
        return new Queue(SysConstant.DEAD_LETTER_QUEUE, true, false, false, map);
    }

    @Bean
    Binding deadLetterBinding(){
        log.debug("将 死信的队列和 死信队列交换机绑定, 并设置用于匹配键：{}",SysConstant.DEAD_LETTER_ROUTING);
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(SysConstant.DEAD_LETTER_ROUTING);
    }

    @Bean
    DirectExchange resendMessageExchange(){
        log.debug("创建 -延时队列转发的交换机-名称为：{}",SysConstant.RESEND_MESSAGE_EXCHANGE);
        return new DirectExchange(SysConstant.RESEND_MESSAGE_EXCHANGE);
    }

    @Bean
    public Queue resendMessageQueue(){
        log.debug("创建 -用于获取延时消息的队列-名称为：{}",SysConstant.RESEND_MESSAGE_QUEUE);
        return new Queue(SysConstant.RESEND_MESSAGE_QUEUE,true,false,false);
    }


    @Bean
    Binding resendMessageBinding(){
        log.debug("将 延时消息的队列和 死信队列交换机绑定, 并设置用于匹配键：{}",SysConstant.RESEND_MESSAGE_ROUTING);
        return BindingBuilder.bind(resendMessageQueue())
                .to(resendMessageExchange())
                .with(SysConstant.RESEND_MESSAGE_ROUTING);
    }

}
