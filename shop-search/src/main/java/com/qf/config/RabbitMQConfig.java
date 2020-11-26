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
    @Value("${exchange}")
    private String exchange;

    @Value("${goodsQueue}")
    private String queue;

    //交换机
    @Bean
    public TopicExchange goodsExchange(){
        return new TopicExchange(exchange,true,false);
    }

    //队列
    @Bean
    public Queue goodsEsQueue(){
        return new Queue(queue,true,false,false);
    }

    //把队列绑定到交换机
    @Bean
    public Binding goodsEsQueueToGoodsExchange(TopicExchange goodsExchange,Queue goodsEsQueue){
        return BindingBuilder.bind(goodsEsQueue).to(goodsExchange).with("goods.*");
    }
}
