package ru.otus.services;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.otus.domain.Child;
import ru.otus.domain.TrainingWork;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class LessonServiceImpl implements LessonService {

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public List<Child> work(TrainingWork trainingWork) {
        log.info("Lesson {}", trainingWork.getTrainingName());

        log.info("Lesson {} done", trainingWork.getTrainingName());

        return new ArrayList<>() {{
            for (String chind : trainingWork.getChildName()) {
                add(new Child(chind, trainingWork.getTrainingName(), getRandomNumber(0, 10)));
            }
        }};
    }
}
