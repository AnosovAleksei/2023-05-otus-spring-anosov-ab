package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.tools.Console;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryApp {

    public static void main(String[] args) {
//        try {
//            Console.main(args);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        SpringApplication.run(LibraryApp.class, args);

    }

}
