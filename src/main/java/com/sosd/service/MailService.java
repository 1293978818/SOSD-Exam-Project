package com.sosd.service;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;

/**
 * 用于发送邮件验证码的服务类
 * @author 应国浩
 */
public interface MailService {
    
    /**
     * 给指定邮件发送邮件验证码
     * @param mail
     * @throws MessagingException 
     * @throws UnsupportedEncodingException 
     */
    public void sendCodeByLogin(String mail) throws UnsupportedEncodingException, MessagingException;
}
