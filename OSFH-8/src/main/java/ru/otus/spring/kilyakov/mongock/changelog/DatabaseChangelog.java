package ru.otus.spring.kilyakov.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.GenreRepository;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", runAlways = true, author = "")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "")
    public void insertAuthors(AuthorRepository authorRepository) {
        authorRepository.save(Author.builder().id("1").firstName("Ivan").middleName("Ivanovich").lastName("Ivanov")
                .build());
        authorRepository.save(Author.builder().id("2").firstName("Petr").build());
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "")
    public void insertGenres(GenreRepository genreRepository) {
        genreRepository.save(Genre.builder().id("1").name("Tutorial").build());
        genreRepository.save(Genre.builder().id("2").name("Story").build());
    }
}
