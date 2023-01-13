package ru.otus.spring.kilyakov.service;


import ru.otus.spring.kilyakov.domain.Genre;

import java.util.List;

public interface GenreService {

    void insert(Genre genre);

    void update(Genre genre);

    Genre getById(String id);

    List<Genre> getAll();

    void deleteById(String id);

}
