package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book book);

    Book update(Book book);

    Optional<Book> getById(Long id);

    List<BookDto> getAll();

    int deleteById(Long id);
}
