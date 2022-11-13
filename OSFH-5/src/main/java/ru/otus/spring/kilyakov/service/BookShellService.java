package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookShellService {

    public String insert(String name, Long authorId, Long bookId);

    public Book getById(UUID id);

    public List<Book> getAll();

    public String update(String name, Long authorId, Long bookId);

    public int delete(UUID id);
}
