package ru.otus.spring.kilyakov.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.AuthorService;
import ru.otus.spring.kilyakov.service.BookService;
import ru.otus.spring.kilyakov.service.GenreService;

import java.util.List;

@RestController()
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/all")
    public List<BookDto> list() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDto list(@PathVariable Long id) {
        return bookService.getById(id);
    }

}
