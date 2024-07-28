package com.arturbik.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    String botUsername;


    public TelegramBot(String botToken, String botUsername) {
        super(botToken);
        this.botUsername = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {

        log.debug(update.getMessage().getText());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }



}
