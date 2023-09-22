package ru.otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.SQLException;


@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class LibraryApp {

    public static void main(String[] args) {
        try {
            Console.main(args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(LibraryApp.class, args);

    }

}
