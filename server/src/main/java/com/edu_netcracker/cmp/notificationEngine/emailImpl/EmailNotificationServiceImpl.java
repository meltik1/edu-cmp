package com.edu_netcracker.cmp.notificationEngine.emailImpl;

import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import com.edu_netcracker.cmp.configs.MailConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
public class EmailNotificationServiceImpl implements NotificationService {

    private final MailConfig mailConfig;

    public EmailNotificationServiceImpl(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    public JavaMailSender getJavaMailSender() {

        System.out.println(this.mailConfig.getPassword());

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailConfig.getHost());
        mailSender.setPort(this.mailConfig.getPort());
        mailSender.setUsername(this.mailConfig.getUsername());
        mailSender.setPassword(this.mailConfig.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public MimeMessage createMimeMessage(JavaMailSender mailSender, String msg) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom("testFrom@test.com");
        messageHelper.setTo("testTo@test.com");
        messageHelper.setSubject(msg);

        String htmlMsg = "<h3>" + msg + "</h3>";

        mimeMessage.setContent(htmlMsg, "text/html");

        return mimeMessage;
    }

    public SimpleMailMessage createSimpleMessage (JavaMailSender mailSender, String msg) {

        SimpleMailMessage simpleMessage = new SimpleMailMessage();

        simpleMessage.setFrom("testFrom@test.com");
        simpleMessage.setTo("testTo@test.com");
        simpleMessage.setSubject("Hello");
        simpleMessage.setText(msg);

        return simpleMessage;
    }


    public void send(String msg) {

        JavaMailSender mailSender = getJavaMailSender();

        try {
            MimeMessage mimeMessage = createMimeMessage(mailSender, msg);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info(e.toString());
        }
    }
}
