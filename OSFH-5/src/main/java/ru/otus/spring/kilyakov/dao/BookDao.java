package ru.otus.spring.kilyakov.dao;


import ru.otus.spring.kilyakov.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookDao {

    int count();

    Book insert(Book book);

    Book update(Book book);

    Book getById(UUID id);

    List<Book> getAll();

    int deleteById(UUID id);
}
