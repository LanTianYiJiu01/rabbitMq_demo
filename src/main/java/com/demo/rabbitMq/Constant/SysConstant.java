package com.demo.rabbitMq.Constant;

/**
 * 常量
 * @author lenovo
 */
public class SysConstant {

    /**
     *死信交换机
     */
    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";

    /**
     * 死信消息队列
     */
    public static final String DEAD_LETTER_QUEUE ="deadLetterQueue";

    /**
     * 死信消息的routing-key
     */
    public static final String DEAD_LETTER_ROUTING ="deadLetterRouting";

    /**
     * 重发消息的交换机
     */
    public static final String RESEND_MESSAGE_EXCHANGE ="resendMessageExchange";
    /**
     * 重发消息的队列
     */
    public static final String RESEND_MESSAGE_QUEUE ="resendMessageQueue";
    /**
     * 重发消息的routing-key
     */
    public static final String RESEND_MESSAGE_ROUTING ="resendMessageRouting";
}
