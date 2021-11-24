package com.demo.rabbitMq.receiverMessage;

import cn.hutool.core.date.DateUtil;
import com.demo.rabbitMq.Constant.SysConstant;
import com.demo.rabbitMq.config.DelayedRabbitMQConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 消费者
 * 监听的队列名称 TestDirectQueue
 * @author lenovo
 */
@Component
@Slf4j
public class ReceiverMessageDirect {

    @RabbitListener(queues = SysConstant.RESEND_MESSAGE_QUEUE)
    public void RepeatTradeReceiver (String msg){
        log.info("时间：{}，监听队列：{}，进行消费的信息为：{}", DateUtil.formatDateTime(new Date()), SysConstant.RESEND_MESSAGE_QUEUE,msg);
    }

    @RabbitListener(queues = DelayedRabbitMQConfig.DELAYED_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},延时队列收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
