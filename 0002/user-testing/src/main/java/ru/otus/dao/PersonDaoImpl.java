package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.dto.Person;
import ru.otus.service.IOService;


@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {


    private final IOService ioService;

    @Override
    public Person getPerson() {


        Person person = new Person();

        ioService.printLn("please enter your name: ");
        person.setName(ioService.readLine());

        ioService.printLn("please enter your surname: ");
        person.setSurname(ioService.readLine());
        return person;
    }
}
