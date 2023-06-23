package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.dto.Person;
import ru.otus.service.IOService;
import ru.otus.service.LocalizationServiceImpl;


@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {


    private final IOService ioService;


    private final LocalizationServiceImpl localizationService;

    @Override
    public Person getPerson() {


        Person person = new Person();


        ioService.printLn(localizationService.getMessage("welcome.name", null) +": ");
        person.setName(ioService.readLine());

        ioService.printLn(localizationService.getMessage("welcome.surname", null) + ": ");
        person.setSurname(ioService.readLine());
        return person;
    }
}
