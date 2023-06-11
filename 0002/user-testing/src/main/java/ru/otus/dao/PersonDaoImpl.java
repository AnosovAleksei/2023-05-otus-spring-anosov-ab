package ru.otus.dao;

import ru.otus.dto.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PersonDaoImpl implements PersonDao {


    @Override
    public Person getPerson() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        Person person = new Person();
        // Reading data using readLine
        try {
            System.out.println("please enter your name: ");
            person.setName(reader.readLine());

            System.out.println("please enter your surname: ");
            person.setSurname(reader.readLine());

            System.out.println("User "+ person.getName() + " "+ person.getSurname());

            return person;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
