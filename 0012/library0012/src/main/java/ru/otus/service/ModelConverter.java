package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.domain.Commentary;

import java.util.List;

public class ModelConverter {
    public static String convertBookToStr(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("book: ");
        sb.append(book.getName());
        sb.append(" [");
        sb.append(book.getAuthor().getName());
        sb.append(" : ");
        sb.append(book.getGenre().getName());

        List<Commentary> commentaryList = book.getCommentaryList();
        if (commentaryList != null && commentaryList.size() > 0) {
            sb.append(" : < ");
            sb.append(String.join(", ", commentaryList.stream().map(s -> convertComentaryToStr(s)).toList()));
            sb.append(" >");
        }
        sb.append("]");
        return sb.toString();
    }

    public static String convertComentaryToStr(Commentary commentary) {
        StringBuilder sb = new StringBuilder();
        sb.append("commentary: ");
        sb.append(commentary.getId());
        sb.append(" [msg:");
        sb.append(commentary.getMessage());
        sb.append(" , book_id : ");
        sb.append(commentary.getBookId());
        sb.append("]");
        return sb.toString();
    }
}
