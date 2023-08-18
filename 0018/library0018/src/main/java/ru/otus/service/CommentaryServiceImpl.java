package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Commentary;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateDto;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentaryRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {

    private final CommentaryRepository commentaryRepository;

    private final BookRepository bookRepository;

    @Override
    public List<Commentary> getAll() {
        return commentaryRepository.findAll();
    }


    @Override
    public List<String> getAllForString() {
        Iterable<Commentary> commentaryList = getAll();
        return new ArrayList<>() {{
            for (Commentary commentary : commentaryList) {
                add(ModelConverter.convertComentaryToStr(commentary));
            }
        }};
    }

    @Override
    @Transactional
    public Commentary create(Commentary commentary) {
        commentaryRepository.save(commentary);
        return commentary;
    }

    @Override
    public Commentary create(CommentaryCreateDto commentaryCreateDto) {
        Commentary commentary = new Commentary();
        commentary.setBookId(commentaryCreateDto.getBookId());
        commentary.setMessage(commentaryCreateDto.getMessage());
        return create(commentary);
    }

    @Override
    @Transactional
    public Commentary read(Long commentaryId) {
        return commentaryRepository.findById(commentaryId).orElse(null);
    }

    @Override
    @Transactional
    public Commentary update(Commentary commentary) {
        return commentaryRepository.save(commentary);
    }

    @Override
    public Commentary update(CommentaryUpdateDto commentaryUpdateDto) {
        Commentary commentary = new Commentary();
        commentary.setBookId(commentaryUpdateDto.getBookId());
        commentary.setMessage(commentaryUpdateDto.getMessage());
        commentary.setId(commentaryUpdateDto.getId());
        return update(commentary);
    }

    @Override
    @Transactional
    public void delete(Commentary commentary) {
        commentaryRepository.delete(commentary);
    }

    @Override
    public List<Commentary> getCommentaryByBookId(Long bookId) {
        return commentaryRepository.findByBookId(bookId);
    }


}

