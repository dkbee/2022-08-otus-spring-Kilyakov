package ru.otus.spring.kilyakov.repository;


import ru.otus.spring.kilyakov.domain.Author;

import java.util.List;

public interface AuthorRepository {

    void insert(Author author);

    void update(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
