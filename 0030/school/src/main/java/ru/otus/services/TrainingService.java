package ru.otus.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.otus.domain.Child;
import ru.otus.domain.TrainingWork;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {
    private final SchoolGateway schoolGateway;

    private List<TrainingWork> createData() {
        return new ArrayList<>() {{
            add(new TrainingWork("marematics", new ArrayList<>() {{
                add("Alex");
                add("Maks");
            }}));
            add(new TrainingWork("language", new ArrayList<>() {{
                add("Anna");
                add("Maks");
            }}));
            add(new TrainingWork("physics", new ArrayList<>() {{
                add("Alex");
                add("Anna");
            }}));
        }};
    }

    @PostConstruct
    public void runWork() {

        List<TrainingWork> trainingWorks = createData();

        log.info("start trainingWork: {}",
                trainingWorks.stream().map(TrainingWork::getTrainingName)
                        .collect(Collectors.joining(",")));

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(() -> {
            List<List<Child>> childListList = schoolGateway.process(trainingWorks);
            for (List<Child> c : childListList) {
                log.info("{} learned: {}", c.get(0).getTraining(),
                        c.stream().map(ch -> ch.getName() + " [received grade = " + ch.getGrate() + "]")
                                .collect(Collectors.joining(",")));
            }
        });
    }

}
