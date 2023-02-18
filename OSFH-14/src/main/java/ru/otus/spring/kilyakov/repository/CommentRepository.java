package ru.otus.spring.kilyakov.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.kilyakov.domain.relational.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
