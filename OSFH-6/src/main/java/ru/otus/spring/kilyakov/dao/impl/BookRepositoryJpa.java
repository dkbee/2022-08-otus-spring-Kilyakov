package ru.otus.spring.kilyakov.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.dao.BookRepository;
import ru.otus.spring.kilyakov.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public Book save(Book book) {
        em.persist(book);
        return book;
    }

    @Transactional
    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Transactional
    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre",
                Book.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete " +
                "from books b " +
                "where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
