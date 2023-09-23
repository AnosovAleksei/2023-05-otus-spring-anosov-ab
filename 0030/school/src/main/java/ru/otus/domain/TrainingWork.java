package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainingWork {

	private final String trainingName;

	private final String[] childName;

}
