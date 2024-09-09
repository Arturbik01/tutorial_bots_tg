package com.arturbik.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumer {

	void answerConsumer(SendMessage sendMessage);

}
