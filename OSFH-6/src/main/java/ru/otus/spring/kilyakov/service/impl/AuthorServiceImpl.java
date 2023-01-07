package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Override
    public void insert(Author author) {
        authorRepository.insert(author);
    }

    @Transactional
    @Override
    public void update(Author author) {
        authorRepository.update(author);
    }

    @Override
    public Author getById(long id) {
        return authorRepository.getById(id);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }
}
