package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateDto;
import ru.otus.repository.AuthorRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @HystrixCommand(commandKey = "getRentsKey")
    @Transactional
    public Author create(Author author) {
        try {
            authorRepository.save(author);
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExistsException("Автор с таким именем уже есть в системе", e);
        }
        return author;

    }

    @Override
    public Author create(AuthorCreateDto authorCreateDto) {
        return create(new Author(authorCreateDto.getName()));
    }


    @HystrixCommand(commandKey = "getRentsKey")
    @Transactional
    public Author update(Author author) {
        authorRepository.findById(author.getId())
                .orElseThrow(() -> new NotFoundException("author with name" + author.getName() + "does not exist"));
        authorRepository.save(author);
        return author;
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    public Author update(AuthorUpdateDto authorUpdateDto) {
        return update(new Author(authorUpdateDto.getId(), authorUpdateDto.getName()));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public Author read(String name) {
        return authorRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("author with name" + name + "does not exist"));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public Author read(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("author with name" + id + "does not exist"));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
