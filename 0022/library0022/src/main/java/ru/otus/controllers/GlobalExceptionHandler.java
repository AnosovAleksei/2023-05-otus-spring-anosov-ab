package ru.otus.controllers;

import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.otus.exeption.AppWorkException;
import ru.otus.exeption.NotFoundException;

/**
 * Глобальный обработчик ошибок web.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Обработка ошибок валидации параметров.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        return Optional.ofNullable(super.handleMethodArgumentNotValid(ex, headers, status, request))
                .map(response -> {
                    Optional.ofNullable(response.getBody())
                            .ifPresent(body -> ((ProblemDetail) body)
                                    .setDetail(ex.getBindingResult()
                                            .getFieldErrors()
                                            .stream()
                                            .map(error ->
                                                    String.join(
                                                            " ",
                                                            error.getField(),
                                                            error.getDefaultMessage())
                                            )
                                            .collect(Collectors.joining(","))
                                    ));
                    return response;
                })
                .orElse(null);
    }

    @ExceptionHandler({NotFoundException.class, AppWorkException.class})
    public ProblemDetail handleAppException(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * Обработка неизвестных исключений.
     */
    @ExceptionHandler({Exception.class})
    public ProblemDetail handleUnknownException(Exception e) {
        log.error("Server error occurred due to " + e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}

