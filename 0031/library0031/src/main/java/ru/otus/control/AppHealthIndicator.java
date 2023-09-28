package ru.otus.control;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.service.BookService;

import java.util.Random;


@RequiredArgsConstructor
@Component
public class AppHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    private final Random random = new Random();

    @Override
    public Health health() {

        Long countBook = bookService.count();

        if (countBook == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Все книги удалены!")
                    .build();
        }
        return Health.up().withDetail("message", "Все нормально!").build();
    }
}
