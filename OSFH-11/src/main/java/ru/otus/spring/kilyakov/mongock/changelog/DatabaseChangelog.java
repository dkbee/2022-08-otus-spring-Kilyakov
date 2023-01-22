package ru.otus.spring.kilyakov.mongock.changelog;

import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeUnit(id = "insertBooks", order = "1", runAlways = true)
public class DatabaseChangelog {

    @Execution
    public void insertAuthors(MongoDatabase db, AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        db.drop();
        authorRepository.save(Author.builder().id("1").firstName("Barnabus").lastName("Stinson")
                .build()).block();
        authorRepository.save(Author.builder().id("2").firstName("Petr").middleName("Ivanovich").build()).block();
        genreRepository.save(Genre.builder().id("1").name("Manual").build()).block();
        genreRepository.save(Genre.builder().id("2").name("Story").build()).block();
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder().comment("blablabla").build());
        bookRepository.save(Book.builder()
                .id("1")
                .name("Bro Code")
                .author(Author.builder().id("1").firstName("Barnabus").lastName("Stinson")
                        .build())
                .genre(Genre.builder().id("1").name("Manual").build())
                .comments(comments)
                .build()).block();
    }

    @RollbackExecution
    public void rollbackExecution() {

    }
}
