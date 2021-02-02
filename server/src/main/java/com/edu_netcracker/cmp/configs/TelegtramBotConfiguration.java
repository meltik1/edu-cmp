package com.edu_netcracker.cmp.configs;


import com.edu_netcracker.cmp.notificationEngine.telegramImpl.NotificationBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@Slf4j
public class TelegtramBotConfiguration {
    @Bean
    public void config() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new NotificationBot());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
