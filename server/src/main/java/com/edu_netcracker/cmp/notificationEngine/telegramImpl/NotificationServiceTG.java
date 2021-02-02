package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


// Пока бесполезный класс который будет имплементировать нормальный NotificationService
@Component
@Primary
public class NotificationServiceTG implements NotificationService {
    @Autowired
    NotificationBot bot;

    @Override
    public void send(Long id, String msg) {
        bot.send(id, msg);
    }
}
