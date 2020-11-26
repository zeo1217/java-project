package com.qf.listener;

import com.qf.entity.Goods;

import com.qf.service.ItemService;
import com.rabbitmq.client.Channel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-09
 */
@Component
@Slf4j
public class GoodsItemQueueListener {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private ItemService itemService;

    @RabbitListener(queues = "${itemQueue}")

    public void addGoods(Goods goods, Channel channel, Message message)throws IOException{
        executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                itemService.createHtml(goods);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false); //手动ack
            }
        });
    }
}
