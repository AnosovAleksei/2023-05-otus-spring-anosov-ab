package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.dao.PersonDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dto.Person;
import ru.otus.dto.QuestionItem;

import java.util.List;

@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;

    private final PersonDao personDao;

    private final UserInteractionDao userInteractionDao;

    private final int passingScore;


    private Person person;

    public boolean userTesting() {
        person = personDao.getPerson();


        List<QuestionItem> questionItems = questionDao.getQuestionItems();
        int count = 0;
        for (QuestionItem questionItem : questionItems) {
            if (userInteractionDao.askQuestion(questionItem)) {
                count++;
            }
        }
        if (passingScore <= count) {
            return true;
        }
        return false;
    }


    public void printRaportTesting(boolean status) {
        StringBuilder sb = new StringBuilder();
        sb.append("user: ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append("\n");
        if (status) {
            sb.append("successfully completed the test");
        } else {
            sb.append("failed testing");
        }
        System.out.println(sb);

    }

    public void printQuestions() {

        List<QuestionItem> questionItems = questionDao.getQuestionItems();

        System.out.println("--------questions-----------");
        for (QuestionItem questionItem : questionItems) {

            StringBuilder sb = new StringBuilder();
            sb.append("question : ");
            sb.append(questionItem.getQuestion());
            sb.append("\n");

            sb.append("answer options : [");
            sb.append(String.join(", ", questionItem.getAnswerOptions().stream().map(s -> s.getBody()).toList()));
            sb.append("]");

            System.out.println(sb.toString());
            System.out.println("-------------------");
        }


    }
}
