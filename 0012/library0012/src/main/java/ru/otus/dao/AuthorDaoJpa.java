package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> getAllAuthor() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> rootEntry = cq.from(Author.class);
        CriteriaQuery<Author> all = cq.select(rootEntry);

        TypedQuery<Author> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Author getAuthorByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name",
                Author.class);
        query.setParameter("name", name);
        List<Author> tempList = query.getResultList();
        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }

    @Transactional
    @Override
    public Author createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        em.persist(author);
        return author;
    }
}