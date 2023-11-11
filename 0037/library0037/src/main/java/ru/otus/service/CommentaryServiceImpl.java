package ru.otus.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Commentary;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateDto;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentaryRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {

    private final CommentaryRepository commentaryRepository;

    private final BookRepository bookRepository;

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public List<Commentary> getAll() {
        return commentaryRepository.findAll();
    }


    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional
    public Commentary create(CommentaryCreateDto commentaryCreateDto) {
        Commentary commentary = new Commentary();
        bookRepository.findById(commentaryCreateDto.getBookId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "book with id : " + commentaryCreateDto.getBookId() + " does not exist"));
        commentary.setBookId(commentaryCreateDto.getBookId());
        commentary.setMessage(commentaryCreateDto.getMessage());
        commentaryRepository.save(commentary);
        return commentary;
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public Commentary read(Long commentaryId) {

        return commentaryRepository.findById(commentaryId).orElseThrow(() ->
                new NotFoundException(
                        "commentary with id : " + commentaryId + " does not exist"));
    }


    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional
    public Commentary update(CommentaryUpdateDto commentaryUpdateDto) {
        Commentary commentary = commentaryRepository.findById(commentaryUpdateDto.getId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "commentary with id : " + commentaryUpdateDto.getId() + " does not exist"));


        bookRepository.findById(commentaryUpdateDto.getBookId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "book with id : " + commentaryUpdateDto.getBookId() + " does not exist"));

        if (commentary.getBookId() != commentaryUpdateDto.getBookId()) {
            throw new AppWorkException(
                    "Изменение автора книги не допускается! Для внесения изменений обращайтесь к администратору");
        }


        commentary.setMessage(commentaryUpdateDto.getMessage());

        commentaryRepository.save(commentary);
        return commentary;
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional
    public void delete(Commentary commentary) {
        commentaryRepository.delete(commentary);
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public List<Commentary> getCommentaryByBookId(Long bookId) {
        return commentaryRepository.findByBookId(bookId);
    }


}

