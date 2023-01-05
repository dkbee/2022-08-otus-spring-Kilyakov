package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.dto.BookDto;

import java.util.List;

public interface BookShellService {

    public String insert(String name, Long authorId, Long bookId);

    public Book getById(Long id);

    public List<BookDto> getAll();

    public String update(Long bookId, String name, Long authorId, Long genreId);

    public String delete(Long id);
}
