package com.edu_netcracker.cmp.configs;


import com.edu_netcracker.cmp.notificationEngine.telegramImpl.NotificationBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@Slf4j
@PropertySource("classpath:tg.properties")
public class TelegtramBotConfiguration {

    @Value("${bot.token}")
    private String token;

    @Autowired
    NotificationBot notificationBot;
    @Bean
    public TelegramBotsApi config() {
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            notificationBot.setToken(token);
            botsApi.registerBot(notificationBot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        return botsApi;
    }
}
