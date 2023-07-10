package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Book {

    private String name;

    private Author author;


    private Genre genre;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("book: ");
        sb.append(name);
        sb.append(" [");
        sb.append(author.getName());
        sb.append(" : ");
        sb.append(genre.getName());
        sb.append("]");
        return sb.toString();
    }
}
