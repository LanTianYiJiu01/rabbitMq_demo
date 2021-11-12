package com.demo.rabbitMq.receiverMessage;

import cn.hutool.core.date.DateUtil;
import com.demo.rabbitMq.Constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
}
