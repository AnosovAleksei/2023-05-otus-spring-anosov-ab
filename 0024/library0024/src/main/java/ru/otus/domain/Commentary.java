package ru.otus.domain;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commentary")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentary_id")
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    private String message;

    @Override
    public String toString() {
        return "Commentary{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
