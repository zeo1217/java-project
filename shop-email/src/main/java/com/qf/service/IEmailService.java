package com.qf.service;

import com.qf.entity.Email;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-13
 */
public interface IEmailService {
    public void sendEmail(Email email) throws Exception;
}
