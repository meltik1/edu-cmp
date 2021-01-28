package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.entities.Contacts;
import com.edu_netcracker.cmp.entities.Students;
import com.edu_netcracker.cmp.entities.jpa.StudentsJpa;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
public class NotificationBot extends TelegramLongPollingBot {

    @Autowired
    StudentsJpa studentsJpa;

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

    public  void send(String mess){
        Students students = studentsJpa.getOne(1l);
        SendMessage message = new SendMessage();
        message.setChatId(students.getContact_fk().getTg_chat_id());
        message.setText(mess);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "NetcrackerNotificationBot";
    }
}
