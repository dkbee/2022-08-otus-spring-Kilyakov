package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kilyakov.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
