package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.config.ResourceProvider;
import ru.otus.dto.Answer;
import ru.otus.dto.QuestionItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class QuestionDaoImpl implements QuestionDao {


    private final ResourceProvider resourceProvider;
//    private String fileName;

    private List<String[]> getData(){

        ClassLoader classLoader = QuestionDaoImpl.class.getClassLoader();
        try ( InputStream fis = classLoader.getResourceAsStream(resourceProvider.getFileResourceName())) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(fis));
            return csvReader.readAll();

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<QuestionItem> getQuestionItems() {
        List<QuestionItem> rez = new ArrayList<>();
        try {
            List<String[]> data = getData();
            for (String[] s : data) {
                if (s != null && s.length > 2) {
                    QuestionItem questionItem = new QuestionItem();
                    questionItem.setQuestion(s[0]);
                    List<Answer> answers = new ArrayList<>();
                    questionItem.setAnswerOptions(answers);
                    answers.add(new Answer(s[1], true));


                    IntStream.range(2,s.length).filter(i -> i > 2).forEach(i->answers.add(new Answer(s[i], false)));

                    rez.add(questionItem);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
}
