package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public BookDto save(Book book) {
        Book savedBook = bookRepository.save(book);
        return getBookDto(savedBook);
    }

    @Transactional
    @Override
    public BookDto update(Book book) {
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        bookOptional.ifPresent(value -> book.setComments(value.getComments()));
        Book updatedBook = bookRepository.save(book);
        return getBookDto(updatedBook);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto getById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book book = bookOptional.orElse(null);
        return getBookDto(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        books.forEach(book -> bookDtoList.add(BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(getAuthorFromProxy(book))
                .genre(getGenreFromProxy(book))
                .build()));
        return bookDtoList;
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private static BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        if (book != null) {
            bookDto = BookDto.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(getAuthorFromProxy(book))
                    .genre(getGenreFromProxy(book))
                    .comments(getCommentsFromProxy(book))
                    .build();
        }
        return bookDto;
    }

    private static Author getAuthorFromProxy(Book book) {
        Author authorProxy = book.getAuthor();
        return Author.builder()
                .id(authorProxy.getId())
                .firstName(authorProxy.getFirstName())
                .middleName(authorProxy.getMiddleName())
                .lastName(authorProxy.getLastName())
                .build();
    }

    private static Genre getGenreFromProxy(Book book) {
        Genre genreProxy = book.getGenre();
        return Genre.builder()
                .id(genreProxy.getId())
                .name(genreProxy.getName())
                .build();
    }

    private static List<Comment> getCommentsFromProxy(Book book) {
        List<Comment> commentsProxy = book.getComments();
        List<Comment> comments = new ArrayList<>();
        if (commentsProxy != null) {
            commentsProxy.forEach(value -> {
                Comment comment = Comment.builder()
                        .id(value.getId())
                        .comment(value.getComment())
                        .build();
                comments.add(comment);
            });
        }
        return comments;
    }
}
