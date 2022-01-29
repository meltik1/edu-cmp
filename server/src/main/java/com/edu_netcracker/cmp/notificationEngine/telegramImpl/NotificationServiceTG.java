package com.edu_netcracker.cmp.notificationEngine.telegramImpl;

import com.edu_netcracker.cmp.entities.repositories.TgUsersInfoRepository;
import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.edu_netcracker.cmp.notificationEngine.IUserMessageInfo;
import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;



@Component
@Primary
public class NotificationServiceTG implements NotificationService {
    @Autowired
    NotificationBot bot;

    @Autowired
    TgUsersInfoRepository tgUsersInfoRepository;


    private final String name = "Telegram";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void send(IUserMessageInfo userMessageInfo, ITemplate template) {
        Map<String, String> contactInfo = userMessageInfo.getMapOfContactId();
        String message = template.getTemplate();
        String userName = contactInfo.get("Telegram");
        if (userName.contains("@")) {
            userName = userName.replace("@", "");
        }
        Long userId = tgUsersInfoRepository.findByUserName(userName).getChatId();

        bot.send(userId.toString(), template.getTemplate());
    }
}
