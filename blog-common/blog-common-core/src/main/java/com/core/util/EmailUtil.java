package com.core.util;

import com.core.modle.bo.EmailBO;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * @author xiongke
 */
@Component
public class EmailUtil {

    private final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    @Value("${spring.mail.username}")
    private String email;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    public void sendHtmlMail(EmailBO emailBO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            Context context = new Context();
            context.setVariables(emailBO.getCommentMap());
            String process = templateEngine.process(emailBO.getTemplate(), context);
            mimeMessageHelper.setFrom(email);
            mimeMessageHelper.setTo(emailBO.getEmail());
            mimeMessageHelper.setSubject(emailBO.getSubject());
            mimeMessageHelper.setText(process, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送email失败:{}", e.getMessage());
        }
    }

}
