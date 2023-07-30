package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(*) from Book", Long.class).getSingleResult();
    }


    @Override
    public void delete(String name) {
        Query query = em.createQuery("delete " +
                "from Book s " +
                "where s.name = :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public Book upgrade(String name, Author author, Genre genre) {

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);

        em.merge(book);

        return book;
    }

    @Override
    public Book update(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book create(Book book) {
        em.persist(book);
        return book;
    }


    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query = em.createQuery("select a from Book a where a.name = :name",
                Book.class);
        query.setParameter("name", name);
        List<Book> tempList = query.getResultList();
        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }

    @Override
    public Book getById(Long bookId) {
        TypedQuery<Book> query = em.createQuery("select a from Book a where a.id = :bookId",
                Book.class);
        query.setParameter("bookId", bookId);
        List<Book> tempList = query.getResultList();
        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }


    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select a from Book a",
                Book.class);
        return query.getResultList();
    }

}
