package com.qf.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-09
 */
@Configuration
public class RabbitMQConfig {
    @Value("${emailChange}")
    private String exchange;

    @Value("${emailQueue}")
    private String queue;

    //交换机
    @Bean
    public TopicExchange emailExchange(){
        return new TopicExchange(exchange,true,false);
    }

    //队列
    @Bean
    public Queue emailQueue(){
        return new Queue(queue,true,false,false);
    }

    //把队列绑定到交换机
    @Bean
    public Binding goodsEsQueueToGoodsExchange(TopicExchange emailQueue,Queue emailEsQueue){
        return BindingBuilder.bind(emailEsQueue).to(emailQueue).with("email.*");
    }
}
