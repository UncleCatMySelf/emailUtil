package com.myself.email.service;

import com.myself.email.EmailApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:19 2018\9\10 0010
 */
public class MailServiceTest extends EmailApplicationTests {

    private static final String to = "1341933031@qq.com";

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() throws Exception {
        mailService.sendSimpleMail("1341933031@qq.com","文本邮件","这是一封文本邮件");
    }

    @Test
    public void sendHtmlMail() throws Exception {
        String content = "<html>\n"+
                "<body\n>"+
                "<h3>hello world , 这是一封html邮件！</h3>\n"+
                "</body>\n"+
                "</html>\n";
        mailService.sendHtmlMail(to,"html邮件",content);
    }

    @Test
    public void sendAttachmentsMail() throws Exception {
        String filePath = "D:\\C语言\\ch7-1 让指针不再困扰你.docx";
        mailService.sendAttachmentsMail(to,"附件邮件","这是一封附件邮件",filePath);
    }

    @Test
    public void sendInlinResourceMail() throws Exception {
        String imgPath = "C:\\Users\\Administrator\\Pictures\\公司\\user.png";
        String rscId = "new001";
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:"+rscId+
                "\'></img></body></html>";
        mailService.sendInlinResourceMail(to,"图片邮件",content,imgPath,rscId);
    }

    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id","01");

        String emailContent = templateEngine.process("emailTemplate",context);

        mailService.sendHtmlMail(to,"模板邮件",emailContent);

    }

}