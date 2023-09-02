package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryDto;
import ru.otus.dto.CommentaryUpdateRequestDto;
import ru.otus.exeption.AppWorkException;
import ru.otus.exeption.NotFoundException;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentaryRepository;

@RequiredArgsConstructor
@RestController
public class CommentaryController {

    private final CommentaryRepository commentaryRepository;

    private final BookRepository bookRepository;

    @GetMapping("/api/v1/commentary")
    public Flux<CommentaryDto> getCommentaryes() {
        return commentaryRepository.findAll().map(c -> new CommentaryDto(c));
    }


    @GetMapping("/api/v1/commentary/{id}")
    public Mono<CommentaryDto> getCommentary(@PathVariable String id) {
        return commentaryRepository.findById(id).map(c -> new CommentaryDto(c));
    }

    @PostMapping("/api/v1/commentary")
    public Mono<CommentaryDto> createCommentary(@Valid @RequestBody CommentaryCreateDto commentaryCreateDto) {
        Book book = bookRepository.findById(commentaryCreateDto.getBookId()).block();
        if (book == null) {
            throw new NotFoundException("book with id " + commentaryCreateDto.getBookId() + " does not exist");
        }

        return commentaryRepository.save(new Commentary(book, commentaryCreateDto.getMessage()))
                .map(c -> new CommentaryDto(c));
    }


    @PutMapping("/api/v1/commentary/{id}")
    public Mono<CommentaryDto> updateCommentary(@PathVariable String id,
                                                @Valid
                                                @RequestBody CommentaryUpdateRequestDto commentaryUpdateRequestDto) {
        Book book = bookRepository.findById(commentaryUpdateRequestDto.getBookId()).block();
        if (book == null) {
            throw new NotFoundException("book with id " + commentaryUpdateRequestDto.getBookId() + " does not exist");
        }
        Commentary commentary = commentaryRepository.findById(id).block();
        if (commentary == null) {
            throw new NotFoundException("commentary with id " + id + " does not exist");
        }

        if (commentary.getBook().getId() != book.getId()) {
            throw new AppWorkException(
                    "Изменение автора книги не допускается! Для внесения изменений обращайтесь к администратору");
        }

        return commentaryRepository.save(new Commentary(id, book, commentaryUpdateRequestDto.getMessage()))
                .map(c -> new CommentaryDto(c));
    }

}
