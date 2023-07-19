package ru.otus.service;

import ru.otus.domain.Book;

public class BookConverter {
    public static String convertBookToStr(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("book: ");
        sb.append(book.getName());
        sb.append(" [");
        sb.append(book.getAuthor().getName());
        sb.append(" : ");
        sb.append(book.getGenre().getName());
        sb.append("]");
        return sb.toString();
    }
}
