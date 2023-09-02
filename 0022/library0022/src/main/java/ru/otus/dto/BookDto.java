package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String id;

    private String name;

    private String authorName;

    private String genreName;

    public BookDto(Book book) {
        id = book.getId();
        name = book.getName();
        authorName = book.getAuthor().getName();
        genreName = book.getGenre().getName();
    }

}
