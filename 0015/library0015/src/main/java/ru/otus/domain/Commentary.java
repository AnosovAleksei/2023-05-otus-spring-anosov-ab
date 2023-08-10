package ru.otus.domain;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "commentary")
public class Commentary {
    @Id
    private String id;


    @DBRef
    private Book book;

    private String message;

    public Commentary(Book book, String message) {
        this.book = book;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Commentary{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
