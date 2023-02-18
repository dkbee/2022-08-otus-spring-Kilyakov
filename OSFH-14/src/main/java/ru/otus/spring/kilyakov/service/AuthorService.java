package ru.otus.spring.kilyakov.service;


import ru.otus.spring.kilyakov.domain.relational.Author;

import java.util.List;

public interface AuthorService {

    void insert(Author author);

    void update(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
