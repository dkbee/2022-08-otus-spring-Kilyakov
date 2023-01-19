package ru.otus.spring.kilyakov.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kilyakov.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByBookId(String bookId);

    void deleteByBookId(String bookId);
}
