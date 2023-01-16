package ru.otus.spring.kilyakov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.AuthorService;
import ru.otus.spring.kilyakov.service.BookService;
import ru.otus.spring.kilyakov.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/all")
    public String listPageView(Model model) {
        return "list";
    }

    @GetMapping("/get")
    public String getBookView(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return "read";
    }

    @GetMapping("/add")
    public String addBookView(Model model) {
        BookDto bookDto = new BookDto();
        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();
        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "edit";
    }


    @GetMapping("/edit")
    public String editBookView(@RequestParam("id") long id, Model model) {
        BookDto book = bookService.getById(id);
        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "edit";
    }


}
