package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.entities.TGUsersInfo;
import com.edu_netcracker.cmp.entities.jpa.TgUsersInfoJPA;
import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.edu_netcracker.cmp.notificationEngine.IUserMessageInfo;
import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;



@Component
@Primary
public class NotificationServiceTG implements NotificationService {
    @Autowired
    NotificationBot bot;

    @Autowired
    TgUsersInfoJPA tgUsersInfoJPA;

    @Override
    public void send(IUserMessageInfo userMessageInfo, ITemplate template) {
        Map<String, String> contactInfo = userMessageInfo.getMapOfContactId();
        String message = template.getTemplate();
        String userName = contactInfo.get("telegram");
        if (userName.contains("@")) {
            userName = userName.replace("@", "");
        }
        TGUsersInfo usersInfo = tgUsersInfoJPA.findByUserName(userName);

        bot.send(usersInfo.getUserName(), "Hello");
    }
}
