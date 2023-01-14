package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Repository;
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

    @Override
    public void insert(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void update(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }
}
