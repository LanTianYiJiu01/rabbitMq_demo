package com.demo.rabbitMq.sendMessage;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.demo.rabbitMq.Constant.SysConstant;
import com.demo.rabbitMq.service.SendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 消息推送
 * @author lenovo
 */
@RestController
@Slf4j
public class SendMessageController {

    @Autowired
    SendMessageService sendMessageService;


    @GetMapping("/deadTest")
    public String deadTest(String msg, String date){

        //long times = getTimeDifference(date);
        Date beginDate = new Date();
        Date endDate = DateUtil.parse(date);
        long times = DateUtil.between(beginDate,endDate, DateUnit.MS);
        log.info("发送时间:{},与当前时间差为：{} 毫秒，msg内容：{}" , DateUtil.formatDateTime(beginDate) ,times,msg);
        sendMessageService.sendDelayMsgToMQ(msg,times);
        log.info("发送时间:{},与当前时间差为：{} 毫秒，msg内容：{}" , DateUtil.formatDateTime(beginDate) ,times,msg);
        return "发生成功！";
    }

    @GetMapping("/deadTest2")
    public String deadTest2(String msg, String date){
        //long times = getTimeDifference(date);
        Date beginDate = new Date();
        Date endDate = DateUtil.parse(date);
        long times = DateUtil.between(beginDate,endDate, DateUnit.MS);
        log.info("发送时间:{},与当前时间差为：{} 毫秒，msg内容：{}" , DateUtil.formatDateTime(beginDate) ,times,msg);
        sendMessageService.sendDelayMsg(msg,Integer.valueOf((int) times));
        log.info("发送时间:{},与当前时间差为：{} 毫秒，msg内容：{}" , DateUtil.formatDateTime(beginDate) ,times,msg);
        return "发生成功！";
    }

    /**
     * 计算 时间差
     * @param date
     * @return
     */
    public long getTimeDifference(String date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String systemTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
                .format(new Date());

        long difference = 0;

        try {
            Date systemDate = dateFormat.parse(systemTime);

            Date paramDate = dateFormat.parse(date);

            difference =  paramDate.getTime() -systemDate.getTime();

            log.info("系统时间：{}，给定时间：{},，给定时间与当前系统时间的差值(以毫秒为单位)：{} 毫秒" , systemTime , date , difference );

        } catch (Exception e) {
            e.printStackTrace();

        }
        return difference;
    }
}
