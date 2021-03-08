package com.edu_netcracker.cmp.notificationEngine.emailImpl;

import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.edu_netcracker.cmp.notificationEngine.IUserMessageInfo;
import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import com.edu_netcracker.cmp.configs.MailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailNotificationServiceImpl implements NotificationService {

    @Autowired
    private MailConfig mailConfig;

    public MimeMessage createMimeMessage(JavaMailSender mailSender, String msg) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom(mailConfig.getFrom());
        messageHelper.setTo("testTo@test.com");
        messageHelper.setSubject(msg);

        String htmlMsg = "<h3>" + msg + "</h3>";

        mimeMessage.setContent(htmlMsg, "text/html");

        return mimeMessage;
    }

    public void send(IUserMessageInfo userMessageInfo, ITemplate template) {

        JavaMailSender mailSender = mailConfig.javaMailSender();

        try {
            MimeMessage mimeMessage = createMimeMessage(mailSender, template.getTemplate());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info(e.toString());
        }
    }
}
