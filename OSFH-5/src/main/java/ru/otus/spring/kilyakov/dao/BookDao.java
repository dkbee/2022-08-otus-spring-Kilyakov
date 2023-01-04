package ru.otus.spring.kilyakov.dao;


import ru.otus.spring.kilyakov.domain.Book;

import java.util.List;

public interface BookDao {

    int insert(Book book);

    int update(Book book);

    Book getById(Long id);

    List<Book> getAll();

    int deleteById(Long id);
}
