package com.arturbik.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface MainService {

	void processTextMessage(Update update);
}
