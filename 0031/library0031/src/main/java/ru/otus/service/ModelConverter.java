package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.domain.Commentary;

import java.util.ArrayList;
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

        sb.append("]");
        return sb.toString();
    }


    public static List<String> allBookDescription(Iterable<Book> books) {
        return new ArrayList<>() {
            {
                for (Book book : books) {
                    add(ModelConverter.convertBookToStr(book));
                }
            }
        };
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

    public static String operationDelateBook(Book book) {
        return "book : " + book.getName() + " deleted successfully";
    }

    public static String operationDelateCommentary(Commentary commentary) {
        return "commentary : " + commentary.getId() + " deleted successfully";
    }
}
