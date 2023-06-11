package ru.otus.dao;

import ru.otus.dto.Answer;
import ru.otus.dto.QuestionItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInteractionDaoImpl implements UserInteractionDao{
    private String askQuestionRaw(String question, String variant){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        try {
            System.out.println("question: "+question + " answer options ["+variant+"]");
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
