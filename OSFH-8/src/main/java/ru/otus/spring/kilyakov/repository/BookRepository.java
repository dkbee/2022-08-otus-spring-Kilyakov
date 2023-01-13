package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kilyakov.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
