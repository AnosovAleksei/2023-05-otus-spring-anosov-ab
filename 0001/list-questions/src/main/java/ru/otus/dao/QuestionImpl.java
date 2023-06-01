package ru.otus.dao;

import lombok.AllArgsConstructor;
import ru.otus.dto.Answer;
import ru.otus.dto.QuestionItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@AllArgsConstructor
public class QuestionImpl implements QuestionDao {

    final private LoadDataDao loadDataService;


    @Override
    public List<QuestionItem> getQuestionItems() {
        List<QuestionItem> rez = new ArrayList<>();
        try {
            List<String[]> data = loadDataService.getData();
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
