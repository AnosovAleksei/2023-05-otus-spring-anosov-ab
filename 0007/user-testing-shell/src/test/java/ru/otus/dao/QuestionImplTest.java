package ru.otus.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.config.ResourceProvider;
import ru.otus.config.TestingProvider;
import ru.otus.dto.QuestionItem;
import ru.otus.service.LocalizationServiceImpl;

import java.util.List;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
public class QuestionImplTest {

    @MockBean
    ResourceProvider resourceProvider;

    @MockBean
    TestingProvider testingProvider;

    @Autowired
    QuestionDaoImpl questionImpl;

    @MockBean
    LocalizationServiceImpl localizationService;

    @Test
    public void testWork(){
        Mockito.when(resourceProvider.getFileResourceName()).thenReturn("data.csv");

        Assertions.assertNotNull(questionImpl);

        List<QuestionItem> QuestionItems = questionImpl.getQuestionItems();

        Assertions.assertEquals(QuestionItems.size(),2);
    }
}
