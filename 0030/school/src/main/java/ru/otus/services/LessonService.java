package ru.otus.services;

import ru.otus.domain.Child;
import ru.otus.domain.TrainingWork;

import java.util.List;

public interface LessonService {

	List<Child> work(TrainingWork trainingWork);
}
