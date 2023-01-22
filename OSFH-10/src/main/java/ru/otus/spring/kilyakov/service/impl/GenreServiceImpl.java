package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.repository.GenreRepository;
import ru.otus.spring.kilyakov.service.GenreService;

import java.util.List;

@Service
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

    @Transactional(readOnly = true)
    @Override
    public Genre getById(long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}
