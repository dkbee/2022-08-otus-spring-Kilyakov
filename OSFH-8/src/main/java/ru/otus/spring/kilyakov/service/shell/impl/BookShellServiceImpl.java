package ru.otus.spring.kilyakov.service.shell.impl;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.BookService;
import ru.otus.spring.kilyakov.service.shell.BookShellService;

import java.util.List;

@ShellComponent
public class BookShellServiceImpl implements BookShellService {
    private final BookService bookService;

    public BookShellServiceImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @ShellMethod(value = "Insert book", key = {"--insert --book", "-i --book"})
    public BookDto insert(String name, String authorId, String genreId) {
        return bookService.save(Book.builder()
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(genreId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Get book by id", key = {"--get --book", "-g --book"})
    public BookDto getById(String id) {
        return bookService.getById(id);
    }

    @Override
    @ShellMethod(value = "Get all books", key = {"--all --book", "-a --book"})
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @Override
    @ShellMethod(value = "Update book", key = {"--update --book", "-u --book"})
    public BookDto update(String bookId, String name, String authorId, String genreId) {
        return bookService.update(Book.builder()
                .id(bookId)
                .name(name)
                .author(Author.builder().id(authorId).build())
                .genre(Genre.builder().id(genreId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Delete book", key = {"--delete --book", "-d --book"})
    public void delete(String id) {
        bookService.deleteById(id);
    }
}