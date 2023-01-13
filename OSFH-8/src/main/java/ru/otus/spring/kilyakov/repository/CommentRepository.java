package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kilyakov.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
