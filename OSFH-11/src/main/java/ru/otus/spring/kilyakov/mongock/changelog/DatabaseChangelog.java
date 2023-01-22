package ru.otus.spring.kilyakov.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", runAlways = true, author = "")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(Author.builder().id("1").firstName("Barnabus").lastName("Stinson")
                .build());
        authorRepository.save(Author.builder().id("2").firstName("Petr").middleName("Ivanovich").build());
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "")
    public void insertGenres(GenreRepository genreRepository) {
        genreRepository.save(Genre.builder().id("1").name("Manual").build());
        genreRepository.save(Genre.builder().id("2").name("Story").build());
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "")
    public void insertBooks(BookRepository bookRepository) {
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder().comment("blablabla").build());
        bookRepository.save(Book.builder()
                .id("1")
                .name("Bro Code")
                .author(Author.builder().id("1").firstName("Barnabus").lastName("Stinson")
                        .build())
                .genre(Genre.builder().id("1").name("Manual").build())
                .comments(comments)
                .build());
    }
}
