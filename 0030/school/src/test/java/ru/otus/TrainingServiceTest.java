package ru.otus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.domain.Child;
import ru.otus.domain.TrainingWork;
import ru.otus.services.SchoolGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;



@SpringBootTest
public class TrainingServiceTest {

    @Autowired
    private SchoolGateway schoolGateway;

    @Test
    public void testFlw(){
        List<TrainingWork> trainingWorks = new ArrayList<>() {{
            add(new TrainingWork("marematics", new ArrayList<>(){{add("Alex"); add("Maks");}}));
            add(new TrainingWork("language", new ArrayList<>(){{add("Anna"); add("Maks");}}));
            add(new TrainingWork("physics", new ArrayList<>(){{add("Alex"); add("Anna");}}));
        }};

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(() -> {
            List<List<Child>> childListList = schoolGateway.process(trainingWorks);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Assertions.assertEquals(childListList.size(), 3);

            Assertions.assertTrue(childListList.get(0).get(0).getName().equals("Alex"));
            Assertions.assertTrue(childListList.get(0).get(0).getTraining().equals("marematics"));
            Assertions.assertTrue(childListList.get(0).get(0).getGrate()>0);


        });

    }
}
