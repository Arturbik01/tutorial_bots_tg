package com.arturbik.service.impl;

import com.arturbik.dao.RawDataDAO;
import com.arturbik.entity.RawData;
import com.arturbik.service.MainService;
import com.arturbik.service.ProducerService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainServiceImpl implements MainService {

	private final RawDataDAO rawDataDAO;
	private final ProducerService producerService;

	public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService) {
		this.rawDataDAO = rawDataDAO;
		this.producerService = producerService;
	}

	@Override
	public void processTextMessage(Update update) {
		saveRawData(update);

		var message = update.getMessage();
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText("IT's node");
		sendMessage.setChatId(message.getChatId());
		producerService.produceAnswer(sendMessage);

	}

	private void saveRawData(Update update) {
		RawData rawData = RawData.builder()
				.event(update)
				.build();
		rawDataDAO.save(rawData);
	}
}
