package ru.otus.service;


import ru.otus.domain.Commentary;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateDto;

import java.util.List;

public interface CommentaryService {

    List<Commentary> getAll();


    List<String> getAllForString();

    Commentary create(Commentary commentary);

    Commentary create(CommentaryCreateDto commentaryCreateDto);

    Commentary read(Long commentaryId);

    Commentary update(Commentary commentary);

    Commentary update(CommentaryUpdateDto commentaryUpdateDto);

    void delete(Commentary commentary);

    List<Commentary> getCommentaryByBookId(Long bookId);

}
