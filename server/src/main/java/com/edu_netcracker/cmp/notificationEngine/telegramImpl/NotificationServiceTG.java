package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


// Пока бесполезный класс который будет имплементировать нормальный NotificationService
@Component
public class NotificationServiceTG implements NotificationService {
    NotificationBot bot;

    @Override
    public void send(String msg) {
        bot.send(msg);
    }
}
