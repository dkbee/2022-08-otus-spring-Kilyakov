package ru.otus.spring.kilyakov.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.kilyakov.domain.relational.Author;

public interface AuthorRepository  extends JpaRepository<Author, Long> {

}
