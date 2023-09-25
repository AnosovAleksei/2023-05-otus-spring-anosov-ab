package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TrainingWork {

	private final String trainingName;

	private final List<String> childName;

}
