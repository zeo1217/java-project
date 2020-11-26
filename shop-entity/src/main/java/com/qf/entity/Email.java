package com.qf.entity;

import lombok.Data;

import javax.security.auth.Subject;
import java.io.Serializable;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-12
 */
@Data
public class Email implements Serializable {
    private String  subject; //标题

    private String to; //收件人

    private String cc; //抄送

    private String content; //邮件内容

    private String from; //发件人
}
