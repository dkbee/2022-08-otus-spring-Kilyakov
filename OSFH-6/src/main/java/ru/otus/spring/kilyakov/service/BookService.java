package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDto save(Book book);

    BookDto update(Book book);

    BookDto getById(Long id);

    List<BookDto> getAll();

    BookDto deleteById(Long id);
}
