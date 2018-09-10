package com.myself.email.service;

import com.myself.email.config.EmailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:50 2018\9\9 0009
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单文本文件
     * @param to 向谁发送
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to ,String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(emailConfig.getFrom());

        //发送邮件
        mailSender.send(message);
    }

    /**
     * 发送HTML邮件
     * @param to 向谁发送
     * @param subject 邮件主题
     * @param content 邮件内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to,String subject,String content) {
        log.info("发送HTML邮件开始：{}，{}，{}",to,subject,content);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(emailConfig.getFrom());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
            log.info("发送HTML邮件成功！");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：",e);
        }
    }

    /**
     * 发送附件邮件
     * @param to 向谁发送
     * @param subject 发送主题
     * @param content 内容
     * @param filePath 文件路径 多文件转数组参数
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to,String subject,String content,String filePath)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(emailConfig.getFrom());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        FileSystemResource file = new FileSystemResource(new File(filePath));

        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        mailSender.send(message);
    }

    /**
     * 带图片的邮件
     * @param to 向谁发送
     * @param subject 主题
     * @param content 内容
     * @param rscPath 图片地址
     * @param rscId 图片id
     * @throws MessagingException
     */
    public void sendInlinResourceMail(String to,String subject,String content,
                                      String rscPath,String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(emailConfig.getFrom());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId,res);
//        helper.addInline(rscId,res);多张图片
        mailSender.send(message);
    }


}
