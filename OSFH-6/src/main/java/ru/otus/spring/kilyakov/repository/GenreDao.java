package ru.otus.spring.kilyakov.repository;


import ru.otus.spring.kilyakov.domain.Genre;

import java.util.List;

public interface GenreDao {

    void insert(Genre genre);

    void update(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
