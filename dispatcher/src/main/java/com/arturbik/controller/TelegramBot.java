package com.arturbik.controller;

import com.arturbik.TelegramConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramConfig telegramConfig;
    private final UpdateController updateController;

	public TelegramBot(TelegramConfig telegramConfig, UpdateController updateController, String telegramBotToken) {
		super(telegramBotToken);
		this.telegramConfig = telegramConfig;
		this.updateController = updateController;
	}

    @Override
    public void onUpdateReceived(Update update) {
		updateController.processUpdate(update);
        log.debug(update.getMessage().getText());
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getBotUsername();
    }

    @SneakyThrows
	@PostConstruct
    public void init() {
        updateController.registerBot(this);
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);

        api.registerBot(this);
    }


    @SneakyThrows
	public void sendAnswerMessage(SendMessage sendMessage) {
        execute(sendMessage);
    }

	@Bean
	public String getTelegramBotToken() {
		return telegramConfig.getBotToken();
	}
}
