package ru.otus.spring.kilyakov.repository;


import ru.otus.spring.kilyakov.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Book update(Book book);

    Optional<Book> getById(Long id);

    List<Book> getAll();

    Book deleteById(Long id);
}
