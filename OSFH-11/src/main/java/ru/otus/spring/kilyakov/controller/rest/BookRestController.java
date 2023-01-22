package ru.otus.spring.kilyakov.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookRepository bookService;

    @GetMapping
    public List<BookDto> list() {
        return bookService.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDto list(@PathVariable String id) {
        Book book = bookService.findById(id).orElse(null);
        return BookDto.toDto(book);
    }

    @PostMapping
    public BookDto add(@RequestBody BookDto bookDto) {
        Book book = bookService.save(bookDto.toDomainObject());
        return BookDto.toDto(book);
    }

    @PutMapping
    public BookDto edit(@RequestBody BookDto bookDto) {
        return BookDto.toDto(bookService.save(bookDto.toDomainObject()));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
    }

}
