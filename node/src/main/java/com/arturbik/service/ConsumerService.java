package com.arturbik.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public interface ConsumerService {

	void consumeTextMessageUpdate(Update update);
	void consumeDocMessageUpdate(Update update);
	void consumePhotoMessageUpdate(Update update);

}
