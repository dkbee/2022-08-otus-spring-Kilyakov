package ru.otus.spring.kilyakov.service.shell.impl;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.domain.relational.Author;
import ru.otus.spring.kilyakov.domain.relational.Book;
import ru.otus.spring.kilyakov.domain.relational.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.BookService;
import ru.otus.spring.kilyakov.service.shell.BookShellService;

import java.sql.SQLException;
import java.util.List;

@ShellComponent
public class BookShellServiceImpl implements BookShellService {
    private final BookService bookService;

    public BookShellServiceImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @ShellMethod(value = "Insert book", key = {"--insert --book", "-i --book"})
    public BookDto insert(String name, Long authorId, Long genreId) {
        return bookService.save(Book.builder()
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(genreId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Get book by id", key = {"--get --book", "-g --book"})
    public BookDto getById(Long id) {
        return bookService.getById(id);
    }

    @Override
    @ShellMethod(value = "Get all books", key = {"--all --book", "-a --book"})
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @Override
    @ShellMethod(value = "Update book", key = {"--update --book", "-u --book"})
    public BookDto update(Long bookId, String name, Long authorId, Long genreId) {
        return bookService.update(Book.builder()
                .id(bookId)
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(genreId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Delete book", key = {"--delete --book", "-d --book"})
    public void delete(Long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "Start console", key = {"-sc"})
    public void startConsole() throws SQLException {
        Console.main();
    }
}