package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.relational.Book;
import ru.otus.spring.kilyakov.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto save(Book book);

    BookDto update(Book book);

    BookDto getById(Long id);

    List<BookDto> getAll();

    void deleteById(Long id);
}
