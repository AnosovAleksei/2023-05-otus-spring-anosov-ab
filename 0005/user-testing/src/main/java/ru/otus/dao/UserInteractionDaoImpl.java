package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.dto.Answer;
import ru.otus.dto.QuestionItem;
import ru.otus.service.IOService;
import ru.otus.service.LocalizationService;


@RequiredArgsConstructor
public class UserInteractionDaoImpl implements UserInteractionDao{
    private final IOService ioService;

    private final LocalizationService localizationService;

    private String askQuestionRaw(String question, String variant){
        ioService.printLn(localizationService.getMessage("question") +
                ": "+
                question +
                " " +
                localizationService.getMessage("answer.options") +
                " ["+variant+"]");
        return ioService.readLine();
    }

    @Override
    public boolean askQuestion(QuestionItem questionItem){
        String question = questionItem.getQuestion();
        String variant = String.join(", ",  questionItem.getAnswerOptions() .stream().map(s->s.getBody()).toList());

        String userAanswer = askQuestionRaw(question, variant);

        for(Answer answer : questionItem.getAnswerOptions()){
            if(answer.getBody().equals(userAanswer) && answer.isCorrect()){
                return true;
            }
        }
        return false;
    }

}
