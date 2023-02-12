package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.kilyakov.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

}
