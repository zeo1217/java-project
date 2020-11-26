package com.qf.shopemail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;


@SpringBootTest
class ShopEmailApplicationTests {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void contextLoads() {
        SimpleMailMessage message=new SimpleMailMessage();

        message.setSubject("电商邮箱注册");
        message.setText("邮件内容<h1>这个一个标签</h1>");
        message.setFrom(from);
        message.setTo("2589484006@qq.com");
        message.setCc("2064901508@qq.com",from);
        javaMailSender.send(message);
    }

    //可以发送html代码
    @Test
    void contextHtml() throws  Exception{
        // 1.封装了邮件的信息
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);

        helper.setSubject("电商邮箱注册");
        helper.setText("邮件内容<h1>这个一个标签</h1>",true);
        helper.setFrom(from);
        helper.setTo("2589484006@qq.com");
        helper.setCc(new String[]{"2064901508@qq.com",from} );
        javaMailSender.send(mimeMessage);
    }

}
