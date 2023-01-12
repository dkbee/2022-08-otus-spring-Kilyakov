package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Repository;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.service.AuthorService;

import java.util.List;

@Repository
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void insert(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void update(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Author getById(long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }
}
