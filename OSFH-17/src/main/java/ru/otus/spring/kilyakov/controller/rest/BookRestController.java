package ru.otus.spring.kilyakov.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.BookService;

import java.util.List;

@RestController()
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> list() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDto list(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    public BookDto add(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto.toDomainObject());
    }

    @PutMapping
    public BookDto edit(@RequestBody BookDto bookDto) {
        return bookService.update(bookDto.toDomainObject());
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }

}
