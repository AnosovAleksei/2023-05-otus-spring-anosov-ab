package ru.otus.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.LocalizationService;
import ru.otus.service.QuestionService;

@RequiredArgsConstructor
@ShellComponent
public class UserTestingController {

    private final LocalizationService localizationService;


    private final QuestionService questionService;


    @ShellMethod(value = "start testing user", key = {"t", "test"})
    public String testingUser() {
        return questionService.startTestingUser();
    }
}
