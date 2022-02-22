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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class EmailNotificationServiceImpl implements NotificationService {

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private JavaMailSender mailSender;


    private  final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public   boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @Autowired
    public EmailNotificationServiceImpl(MailConfig mailConfig, JavaMailSender mailSender) {
        this.mailConfig = mailConfig;
        this.mailSender = mailSender;
    }

    private String name = "Email";

    public MimeMessage createMimeMessage(String msg, String to, String theme) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        messageHelper.setFrom(mailConfig.getFrom());
        messageHelper.setTo(to);
        messageHelper.setSubject(theme);
        messageHelper.setText(msg);

        return mimeMessage;
    }

    @Override
    public String getName() {
        return name;
    }

    public void send(IUserMessageInfo userMessageInfo, ITemplate template) {
        try {

            if (userMessageInfo.getMapOfContactId().get("Email") == null || template.getTemplate() == null
                || template.getTheme() == null) {
                throw new NullPointerException("Email or template is not set");
            }

            if (!validate(userMessageInfo.getMapOfContactId().get("Email"))) {
                throw new IllegalArgumentException("Invalid email structure");
            }

            MimeMessage mimeMessage = createMimeMessage(template.getTemplate(),
                                                        userMessageInfo.getMapOfContactId().get("Email"), template.getTheme());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info(e.toString());
        }
    }
}
