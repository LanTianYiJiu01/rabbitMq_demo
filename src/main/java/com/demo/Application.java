package com.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lenovo
 */
@SpringBootApplication
@EnableRabbit
@Slf4j
public class Application{


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.debug("debug级别输出日志。");
        log.info("info级别输出日志。");
        log.warn("warn级别输出日志。");
        log.error("error级别输出日志。");
    }

}
