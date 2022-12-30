package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookShellService {

    public String insert(String name, Long authorId, Long bookId);

    public Book getById(Long id);

    public List<Book> getAll();

    public String update(Long bookId, String name, Long authorId, Long genreId);

    public String delete(Long id);
}
