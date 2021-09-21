package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.entities.TGUsersInfo;
import com.edu_netcracker.cmp.entities.jpa.TgUsersInfoJPA;
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
    TgUsersInfoJPA tgUsersInfoJPA;


    public void setToken(String token) {
        this.token = token;
    }

    //@Value("bot.token")
    private String token;
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
            message.setText("Привет, ты успешно зарегитрирован в боте, в ближайшем будущем ожидай новых сообщений!");
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public  void send(String id, String mess) {
        SendMessage message = new SendMessage();

        message.setChatId(id);
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
