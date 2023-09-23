package ru.otus.services;

import java.util.List;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.otus.domain.Child;
import ru.otus.domain.TrainingWork;

@MessagingGateway
public interface Scool {

	@Gateway(requestChannel = "itemsChannel", replyChannel = "schoolChannel")
	List<List<Child>> process(List<TrainingWork> trainingWorks);
}
