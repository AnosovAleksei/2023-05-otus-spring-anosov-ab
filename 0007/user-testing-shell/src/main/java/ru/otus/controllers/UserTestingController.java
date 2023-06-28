package ru.otus.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.dto.Person;
import ru.otus.service.IOService;
import ru.otus.service.LocalizationService;
import ru.otus.service.QuestionService;

@RequiredArgsConstructor
@ShellComponent
public class UserTestingController {
    private Person person;

    private final LocalizationService localizationService;

    private final IOService ioService;

    private final QuestionService questionService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String loginUser (@ShellOption String name, @ShellOption String surname) {
        person = new Person();
        person.setName(name);
        person.setSurname(surname);

        StringBuilder sb = new StringBuilder();

        sb.append(localizationService.getMessage("user"));
        sb.append(": ");
        sb.append(person.getName());
        sb.append(" ");
        sb.append(person.getSurname());

        return sb.toString();
    }

    @ShellMethod(value = "Login command", key = {"t", "test"})
    public String testingUser (){
        if(person==null){
            return localizationService.getMessage("loggin.err") +"\n"+ localizationService.getMessage("loggin.msg");
        }

        ioService.printLn(localizationService.getMessage("loggin.ok"));

        questionService.printRaportTesting(questionService.userTesting(person));

        return localizationService.getMessage("work.end");
    }
}
