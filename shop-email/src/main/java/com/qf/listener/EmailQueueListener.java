package com.qf.listener;

import com.qf.entity.Email;
import com.qf.service.IEmailService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
@Slf4j
public class EmailQueueListener  {

    @Autowired
    private IEmailService emailService;

    @RabbitListener(queues = "${emailQueue}")
    public void sendEmail(Email email, Channel channel, Message message) throws Exception {
        log.info("邮件服务监听到了消息:"+email);
        emailService.sendEmail(email);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
