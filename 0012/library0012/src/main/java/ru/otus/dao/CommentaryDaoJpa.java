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
import ru.otus.domain.Commentary;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentaryDaoJpa implements CommentaryDao {


    @PersistenceContext
    private final EntityManager em;

    @Transactional
    @Override
    public Commentary create(Long bookId, String message) {
        Commentary commentary = new Commentary();
        commentary.setBookId(bookId);
        commentary.setMessage(message);
        em.persist(commentary);

        return commentary;
    }

    @Override
    public Commentary read(Long commentaryId) {
        TypedQuery<Commentary> query = em.createQuery("select a from Commentary a where a.id = :commentaryId",
                Commentary.class);
        query.setParameter("commentaryId", commentaryId);
        List<Commentary> tempList = query.getResultList();
        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }

    @Transactional
    @Override
    public Commentary update(Commentary commentary) {
        em.merge(commentary);
        return commentary;
    }

    @Transactional
    @Override
    public String delate(Commentary commentary) {
        Long commentaryId = commentary.getId();
        em.remove(commentary);
//        em.flush();

//        Long commentaryId  = commentary.getId();
//        Query query = em.createQuery("delete " +
//                "from Commentary s " +
//                "where s.id = :commentaryId");
//        query.setParameter("commentaryId", commentaryId);
//        query.executeUpdate();
        return "commentary (id) : " + commentaryId + " deleted successfully";
    }

    @Override
    public List<Commentary> getAllCommentary() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Commentary> cq = cb.createQuery(Commentary.class);
        Root<Commentary> rootEntry = cq.from(Commentary.class);
        CriteriaQuery<Commentary> all = cq.select(rootEntry);

        TypedQuery<Commentary> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
