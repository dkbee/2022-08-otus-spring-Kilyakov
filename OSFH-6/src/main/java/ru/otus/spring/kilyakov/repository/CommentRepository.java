package ru.otus.spring.kilyakov.repository;


import ru.otus.spring.kilyakov.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Comment update(Comment comment);

    Optional<Comment> getById(Long id);

    Comment deleteById(Long id);

}
