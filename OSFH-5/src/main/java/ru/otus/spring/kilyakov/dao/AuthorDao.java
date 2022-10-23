package ru.otus.spring.kilyakov.dao;


import ru.otus.spring.kilyakov.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);
}
