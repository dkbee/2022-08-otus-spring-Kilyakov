package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.kilyakov.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

}
