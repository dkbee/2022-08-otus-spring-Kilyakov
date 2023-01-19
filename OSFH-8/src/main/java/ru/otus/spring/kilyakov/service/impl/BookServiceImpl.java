package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.service.BookService;
import ru.otus.spring.kilyakov.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CommentService commentService;

    public BookServiceImpl(BookRepository bookRepository, CommentService commentService) {
        this.bookRepository = bookRepository;
        this.commentService = commentService;
    }

    @Override
    public BookDto save(Book book) {
        Book savedBook = bookRepository.save(book);
        return getBookDto(savedBook);
    }

    @Override
    public BookDto update(Book book) {
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        bookOptional.ifPresent(value -> book.setComments(value.getComments()));
        Book updatedBook = bookRepository.save(book);
        return getBookDto(updatedBook);
    }

    @Override
    public BookDto getById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book book = bookOptional.orElse(null);
        return getBookDto(book);
    }

    public List<BookDto> getAll() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        books.forEach(book -> bookDtoList.add(BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor() != null && book.getAuthor().getFirstName() != null ? book.getAuthor() : null)
                .genre(book.getGenre() != null && book.getGenre().getName() != null ? book.getGenre() : null)
                .build()));
        return bookDtoList;
    }

    @Override
    public void deleteById(String id) {
        commentService.deleteByBookId(id);
        bookRepository.deleteById(id);
    }

    private static BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        if (book != null) {
            bookDto = BookDto.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .author(book.getAuthor() != null && book.getAuthor().getFirstName() != null ? book.getAuthor() : null)
                    .genre(book.getGenre() != null && book.getGenre().getName() != null ? book.getGenre() : null)
                    .comments(book.getComments() != null && book.getComments().size() > 0 ? book.getComments() : null)
                    .build();
        }
        return bookDto;
    }
}
