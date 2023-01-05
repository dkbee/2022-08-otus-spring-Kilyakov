package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.repository.GenreRepository;
import ru.otus.spring.kilyakov.service.GenreService;

import java.util.List;

@Repository
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    @Override
    public void insert(Genre genre) {
        genreRepository.insert(genre);
    }

    @Transactional
    @Override
    public void update(Genre genre) {
        genreRepository.update(genre);
    }

    @Transactional
    @Override
    public Genre getById(long id) {
        return genreRepository.getById(id);
    }

    @Transactional
    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}
