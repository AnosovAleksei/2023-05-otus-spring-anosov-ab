package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.config.TestingProvider;
import ru.otus.dao.PersonDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dto.Person;
import ru.otus.dto.QuestionItem;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;

    private final PersonDao personDao;

    private final UserInteractionDao userInteractionDao;

    private final IOService ioService;

    private final LocalizationService localizationService;

    private final TestingProvider testingProvider;





    private Person person;

    public boolean userTesting(Person person){

        this.person = person;
        List<QuestionItem> questionItems = questionDao.getQuestionItems();
        int count = 0;
        for (QuestionItem questionItem : questionItems) {
            if (userInteractionDao.askQuestion(questionItem)) {
                count++;
            }
        }
        if (testingProvider.getPassingScore() <= count) {
            return true;
        }
        return false;

    }

    public boolean userTesting() {
        person = personDao.getPerson();
        return userTesting(person);
    }


    public void printRaportTesting(boolean status) {
        StringBuilder sb = new StringBuilder();

        sb.append(localizationService.getMessage("user"));
        sb.append(": ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());
        sb.append("\n");
        if (status) {
            sb.append(localizationService.getMessage("status.ok"));
        } else {
            sb.append(localizationService.getMessage("status.err"));
        }
        ioService.printLn(sb.toString());

    }

    public void printQuestions() {

        List<QuestionItem> questionItems = questionDao.getQuestionItems();

        ioService.printLn("--------questions-----------");
        for (QuestionItem questionItem : questionItems) {

            StringBuilder sb = new StringBuilder();
            sb.append("question : ");
            sb.append(questionItem.getQuestion());
            sb.append("\n");

            sb.append("answer options : [");
            sb.append(String.join(", ", questionItem.getAnswerOptions().stream().map(s -> s.getBody()).toList()));
            sb.append("]");

            ioService.printLn(sb.toString());
            ioService.printLn("-------------------");
        }


    }

//    @PostConstruct
    public void workService(){
        printRaportTesting(userTesting());
    }
}
