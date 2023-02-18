package ru.otus.spring.kilyakov.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.kilyakov.domain.relational.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
