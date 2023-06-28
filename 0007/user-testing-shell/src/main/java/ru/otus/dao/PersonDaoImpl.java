package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dto.Person;
import ru.otus.service.IOService;
import ru.otus.service.LocalizationService;


@Component
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {


    private final IOService ioService;


    private final LocalizationService localizationService;

    @Override
    public Person getPerson() {


        Person person = new Person();

        person.setName(ioService.readLine(localizationService.getMessage("welcome.name") +": "));

        person.setSurname(ioService.readLine(localizationService.getMessage("welcome.surname") + ": "));
        return person;
    }
}
