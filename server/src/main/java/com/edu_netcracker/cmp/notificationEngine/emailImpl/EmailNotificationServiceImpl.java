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

    @Autowired
    private JavaMailSender mailSender;

    private String name = "Email";

    public MimeMessage createMimeMessage(String msg, String to) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        messageHelper.setFrom(mailConfig.getFrom());
        messageHelper.setTo(to);
        messageHelper.setSubject(msg);
        messageHelper.setText(msg);

        return mimeMessage;
    }

    @Override
    public String getName() {
        return name;
    }

    public void send(IUserMessageInfo userMessageInfo, ITemplate template) {
        try {
            MimeMessage mimeMessage = createMimeMessage(template.getTemplate(),
                                                        userMessageInfo.getMapOfContactId().get("Email"));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info(e.toString());
        }
    }
}
