package com.arturbik;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yaml")
@Data
public class TelegramConfig {

    @Value("${bot.telegram.token}")
    private String botToken;

    @Value("${bot.telegram.username}")
    private String botUsername;

    @Bean
    public String telegramBotToken() {
        return botToken;
    }


//    @Bean
//    public TelegramBot getTelegramBot() throws TelegramApiException {
//        TelegramBot telegramBot = new TelegramBot(getBotToken(), getBotUsername());
//        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
//
//        api.registerBot(telegramBot);
//        return telegramBot;
//    }

}
