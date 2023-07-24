package ru.otus.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
        Root<Genre> rootEntry = cq.from(Genre.class);
        CriteriaQuery<Genre> all = cq.select(rootEntry);

        TypedQuery<Genre> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select a from Genre a where a.name = :name",
                Genre.class);
        query.setParameter("name", name);
        List<Genre> tempList = query.getResultList();
        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }


    @Override
    public Genre create(Genre genre) {
        em.persist(genre);
        return genre;
    }
}
