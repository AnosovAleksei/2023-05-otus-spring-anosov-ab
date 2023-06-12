package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.dto.Answer;
import ru.otus.dto.QuestionItem;
import ru.otus.service.IOService;


@RequiredArgsConstructor
public class UserInteractionDaoImpl implements UserInteractionDao{
    private final IOService ioService;

    private String askQuestionRaw(String question, String variant){
        ioService.printLn("question: "+question + " answer options ["+variant+"]");
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
