package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.kilyakov.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
