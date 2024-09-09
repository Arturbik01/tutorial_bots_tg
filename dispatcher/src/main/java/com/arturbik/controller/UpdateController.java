package com.arturbik.controller;

import com.arturbik.RabbitQueue;
import com.arturbik.service.UpdateProducer;
import com.arturbik.utils.MessageUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Log4j
public class UpdateController {
	private TelegramBot telegramBot;
	private final MessageUtils messageUtils;
	private final UpdateProducer updateProducer;

	public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
		this.messageUtils = messageUtils;
		this.updateProducer = updateProducer;
	}


	public void registerBot(TelegramBot telegramBot) {
		this.telegramBot = telegramBot;
	}

	public void processUpdate(Update update) {
		if (update == null) {
			log.error("Received update is null");
			return;
		}

		if (update.getMessage() != null) {
			distributeMessagesByType(update);
		} else {
			log.error("Received is unsupported message type " + update);
		}
	}

	private void distributeMessagesByType(Update update) {
		var message = update.getMessage();
		if (message.getText() != null) {
			processTextMessage(update);
		} else if (message.getDocument() != null) {
			processDocMessage(update);
		} else if (message.getPhoto() != null) {
			processPhotoMessage(update);
		} else {
			setUnsupportMessageTypeView(update);
		}
	}

	private void setUnsupportMessageTypeView(Update update) {
		var sendMessage = messageUtils.generateSendMessageWithText(update, "Не знаю, что ответить");
		setView(sendMessage);
	}

	public void setView(SendMessage sendMessage) {
		telegramBot.sendAnswerMessage(sendMessage);
	}

	private void processPhotoMessage(Update update) {
		updateProducer.produce(RabbitQueue.PHOTO_MESSAGE_UPDATE, update);
		setFileIsReceivedView(update);
	}

	private void setFileIsReceivedView(Update update) {
		var sendMessage = messageUtils.generateSendMessageWithText(update, "Файл загружен");
		setView(sendMessage);


	}

	private void processDocMessage(Update update) {
		updateProducer.produce(RabbitQueue.DOC_MESSAGE_UPDATE, update);
	}

	@SneakyThrows
	private void processTextMessage(Update update) {
		updateProducer.produce(RabbitQueue.TEXT_MESSAGE_UPDATE, update);

		Message textMessage = update.getMessage();
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText(String.format("%s%n%s", "Бот в разработке", textMessage.getText()));
		sendMessage.setChatId(textMessage.getChatId());
		telegramBot.execute(sendMessage);

	}

}
