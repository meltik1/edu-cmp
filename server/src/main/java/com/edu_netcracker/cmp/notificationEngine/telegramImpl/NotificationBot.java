package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.TGUsersInfo;
import com.edu_netcracker.cmp.entities.jpa.STAJPA;
import com.edu_netcracker.cmp.entities.jpa.StudentsJpa;
import com.edu_netcracker.cmp.entities.jpa.TgUsersInfoJPA;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Slf4j
@Component
@PropertySource("classpath:tg.properties")
public class NotificationBot extends TelegramLongPollingBot {

    @Autowired
    StudentsJpa studentsJpa;
    @Autowired
    STAJPA stajpa;
    @Autowired
    TgUsersInfoJPA tgUsersInfoJPA;


    //@Value("bot.token")
    private String token = "1531954543:AAHgZt4bem3JygKOQ69JSD-oM51odOA2POg";
    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.debug(update.toString());
            String name = update.getMessage().getText();
            if (name.equals("/start")) {
                TGUsersInfo tgUsersInfo = new TGUsersInfo();
                tgUsersInfo.setChatId(update.getMessage().getChatId());
                tgUsersInfo.setUserName(update.getMessage().getFrom().getUserName());
                tgUsersInfoJPA.save(tgUsersInfo);
            }


            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(name);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public  void send(Long id, String mess) {
        SendMessage message = new SendMessage();
        List<StudentsToAttributes> studentsToAttributesList = stajpa.findStudentAttributeValue(id, "tg_chat_id");
        for (StudentsToAttributes studentsToAttributes : studentsToAttributesList) {
            log.info("Sending msg to student with id {{}}" , id);
            message.setChatId(studentsToAttributes.getChar_value());
            message.setText("Hello, " + studentsToAttributes.getStudent().getFirstName());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "NetcrackerNotificationBot";
    }
}
