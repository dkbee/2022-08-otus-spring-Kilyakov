package ru.otus.spring.kilyakov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.GenreRepository;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    @GetMapping("/all")
    public String listPageView(Model model) {
        return "list";
    }

    @GetMapping("/get")
    public String getBookView(@RequestParam("id") String id, Model model) {
        model.addAttribute("id", id);
        return "read";
    }

    @GetMapping("/add")
    public String addBookView(Model model) {
        BookDto bookDto = new BookDto();
        Flux<Author> authors = authorRepository.findAll();
        Flux<Genre> genres = genreRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", bookDto);

        return "edit";
    }


    @GetMapping("/edit")
    public String editBookView(@RequestParam("id") String id, Model model) {
        Flux<Author> authors = authorRepository.findAll();
        Flux<Genre> genres = genreRepository.findAll();
        Mono<Book> book = bookRepository.findById(id);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "edit";
    }


}
