package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.kilyakov.domain.relational.Author;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.service.AuthorService;

import java.util.List;

@Service
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
