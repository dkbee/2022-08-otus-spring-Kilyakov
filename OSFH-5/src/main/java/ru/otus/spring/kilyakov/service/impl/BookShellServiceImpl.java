package ru.otus.spring.kilyakov.service.impl;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.dao.BookDao;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.service.BookShellService;

import java.sql.SQLException;
import java.util.List;

@ShellComponent
public class BookShellServiceImpl implements BookShellService {

    private final BookDao bookDao;

    public BookShellServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    @ShellMethod(value = "Insert book", key = {"--insert --book", "-i --book"})
    public String insert(String name, Long authorId, Long bookId) {
        return "Count of books inserted: " + bookDao.insert(Book.builder()
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(bookId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Get book by id", key = {"--get --book", "-g --book"})
    public Book getById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    @ShellMethod(value = "Get all books", key = {"--all --book", "-a --book"})
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    @ShellMethod(value = "Update book", key = {"--update --book", "-u --book"})
    public String update(Long bookId, String name, Long authorId, Long genreId) {
        return "Count of books updated: " + bookDao.update(Book.builder()
                .id(bookId)
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(genreId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Delete book", key = {"--delete --book", "-d --book"})
    public String delete(Long id) {
        return "Count of books deleted: " + bookDao.deleteById(id);
    }

    @ShellMethod(value = "Start console", key = {"-sc"})
    public void startConsole() throws SQLException {
        Console.main();
    }
}