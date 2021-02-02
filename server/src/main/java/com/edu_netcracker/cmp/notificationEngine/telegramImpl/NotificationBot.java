package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.entities.Students;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.jpa.STAJPA;
import com.edu_netcracker.cmp.entities.jpa.StudentsJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


@Slf4j
@Component
public class NotificationBot extends TelegramLongPollingBot {

    @Autowired
    StudentsJpa studentsJpa;
    @Autowired
    STAJPA stajpa;

    @Override
    public String getBotToken() {
        return "1531954543:AAHgZt4bem3JygKOQ69JSD-oM51odOA2POg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.debug(update.toString());
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Hello");
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public  void send(Long id, String mess) {
        SendMessage message = new SendMessage();
        List<StudentsToAttributes> studentsToAttributesList = stajpa.findByStudentIdAAndAttributes_Attribute_name(id, "tg_chat_id");
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
