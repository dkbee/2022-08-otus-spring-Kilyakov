package ru.otus.spring.kilyakov.repository.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Comment deleteById(Long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
        return comment;
    }

}
