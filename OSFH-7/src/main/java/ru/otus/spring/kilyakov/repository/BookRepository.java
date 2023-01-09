package ru.otus.spring.kilyakov.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.kilyakov.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
