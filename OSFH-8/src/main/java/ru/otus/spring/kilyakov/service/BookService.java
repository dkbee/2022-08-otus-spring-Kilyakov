package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto save(Book book);

    BookDto update(Book book);

    BookDto getById(String id);

    List<BookDto> getAll();

    void deleteById(String id);
}
