package ru.otus.spring.kilyakov.service.impl;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.dao.BookDao;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.service.BookShellService;

import java.util.List;
import java.util.UUID;

@ShellComponent
public class BookShellServiceImpl implements BookShellService {

    private final BookDao bookDao;

    public BookShellServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    @ShellMethod(value = "Insert book", key = {"--insert book", "-i book"})
    public String insert(String name, Long authorId, Long bookId) {
        return bookDao.insert(Book.builder()
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(bookId).build())
                .build()).toString();
    }

    @Override
    @ShellMethod(value = "Get book by id", key = {"--get book", "-g book"})
    public Book getById(UUID id) {
        return bookDao.getById(id);
    }

    @Override
    @ShellMethod(value = "Get all books", key = {"--all book", "-a book"})
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    @ShellMethod(value = "Update book", key = {"--update book", "-u book"})
    public String update(String name, Long authorId, Long bookId) {
        return bookDao.update(Book.builder()
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(bookId).build())
                .build()).toString();
    }

    @Override
    @ShellMethod(value = "Delete book", key = {"--delete book", "-d book"})
    public int delete(UUID id) {
        return bookDao.deleteById(id);
    }

}