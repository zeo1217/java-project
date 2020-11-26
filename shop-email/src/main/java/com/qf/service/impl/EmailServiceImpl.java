package com.qf.service.impl;

import com.qf.entity.Email;
import com.qf.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(Email email) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // MimeMessageHelper：可以发送html代码的
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);


        helper.setSubject(email.getSubject());
        helper.setText(email.getContent(), true);
        helper.setFrom(from);
        helper.setTo(email.getTo()); // 发送
        helper.setCc(new String[]{from}); // 抄送
        if (!StringUtils.isEmpty(email.getCc())) {
            helper.setCc(new String[]{email.getCc(), from}); // 抄送
        }

        javaMailSender.send(mimeMessage);
    }
}
