package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kilyakov.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
