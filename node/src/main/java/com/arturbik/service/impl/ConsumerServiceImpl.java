package com.arturbik.service.impl;

import com.arturbik.service.ConsumerService;
import com.arturbik.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.arturbik.RabbitQueue.*;

@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
	private final MainService mainService;

	public ConsumerServiceImpl(MainService mainService) {
		this.mainService = mainService;
	}

	@Override
	@RabbitListener(queues = TEXT_MESSAGE_UPDATE)
	public void consumeTextMessageUpdate(Update update) {
		log.debug("NODE: Text message is received");
		mainService.processTextMessage(update);
	}

	@Override
	@RabbitListener(queues = DOC_MESSAGE_UPDATE)
	public void consumeDocMessageUpdate(Update update) {
		log.debug("NODE: Doc message is received");

	}

	@Override
	@RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
	public void consumePhotoMessageUpdate(Update update) {
		log.debug("NODE: Photo message is received");
	}
}
