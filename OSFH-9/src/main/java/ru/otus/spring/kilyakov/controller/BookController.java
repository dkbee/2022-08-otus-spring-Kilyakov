package ru.otus.spring.kilyakov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.AuthorService;
import ru.otus.spring.kilyakov.service.BookService;
import ru.otus.spring.kilyakov.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = bookService.getAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/get")
    public String listPage(@RequestParam("id") long id, Model model) {
        BookDto book = bookService.getById(id);
        model.addAttribute("book", book);
        return "read";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        BookDto book = bookService.getById(id);
        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateBook(BookDto book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.update(book.toDomainObject());
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        BookDto bookDto = new BookDto();
        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();
        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "edit";
    }

    @PostMapping("/add")
    public String createBook(BookDto book,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.save(book.toDomainObject());
        return "redirect:/";
    }
}
