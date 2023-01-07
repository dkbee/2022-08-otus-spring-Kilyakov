package ru.otus.spring.kilyakov.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Transactional
    @Override
    public Comment update(Comment comment) {
        return em.merge(comment);
    }

    @Transactional
    @Override
    public Optional<Comment> getById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Transactional
    @Override
    public List<Comment> getAllForBook(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Comment deleteById(Long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
        return comment;
    }
    @Transactional
    @Override
    public int deleteAllForBook(Long bookId) {
        Query query = em.createQuery("delete from Comment c where c.book.id = :bookId");
        query.setParameter("bookId", bookId);
        return query.executeUpdate();
    }
}
