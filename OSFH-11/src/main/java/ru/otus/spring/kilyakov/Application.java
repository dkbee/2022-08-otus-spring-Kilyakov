package ru.otus.spring.kilyakov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        var context = SpringApplication.run(Application.class, args);
        var bookRepository = context.getBean(BookRepository.class);
        var authorRepository = context.getBean(AuthorRepository.class);
        var genreRepository = context.getBean(GenreRepository.class);

        authorRepository.saveAll(Arrays.asList(
                Author.builder().id("1").firstName("Barnabus").lastName("Stinson")
                        .build(), Author.builder().id("2").firstName("Petr").middleName("Ivanovich").build()
        )).subscribe(author -> logger.info("author :{}", author));

        genreRepository.saveAll(Arrays.asList(
                Genre.builder().id("1").name("Manual").build(), Genre.builder().id("2").name("Story").build()
        )).subscribe(genre -> logger.info("genre :{}", genre));

        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder().comment("blablabla").build());

        bookRepository.save(Book.builder()
                .id("1")
                .name("Bro Code")
                .author(Author.builder().id("1").firstName("Barnabus").lastName("Stinson")
                        .build())
                .genre(Genre.builder().id("1").name("Manual").build())
                .comments(comments)
                .build()).subscribe(book -> logger.info("book :{}", book));

        System.out.printf("%n%s%n", "http://localhost:8080/all");
    }
}
