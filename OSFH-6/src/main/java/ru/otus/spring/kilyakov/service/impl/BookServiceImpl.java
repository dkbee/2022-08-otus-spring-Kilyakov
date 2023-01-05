package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.dao.BookRepository;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.dto.BookDto;
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

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.update(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.getById(id);
    }

    @Transactional
    @Override
    public List<BookDto> getAll() {
        List<Book> books = bookRepository.getAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        books.forEach(book -> bookDtoList.add(BookDto.builder()
                .name(book.getName())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .build()));
        return bookDtoList;
    }

    @Override
    public int deleteById(Long id) {
        return bookRepository.deleteById(id);
    }
}
