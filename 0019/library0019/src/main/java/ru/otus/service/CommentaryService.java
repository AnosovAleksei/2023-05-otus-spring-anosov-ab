package ru.otus.service;


import ru.otus.domain.Commentary;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateDto;

import java.util.List;

public interface CommentaryService {

    List<Commentary> getAll();


    Commentary create(CommentaryCreateDto commentaryCreateDto);

    Commentary read(Long commentaryId);

    Commentary update(CommentaryUpdateDto commentaryUpdateDto);

    void delete(Commentary commentary);

    List<Commentary> getCommentaryByBookId(Long bookId);

}
