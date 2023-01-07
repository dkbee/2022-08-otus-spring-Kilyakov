package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;

public interface CommentService {
    CommentDto save(Comment comment);

    CommentDto update(Comment comment);

    CommentDto getById(Long id);

    void deleteById(Long id);

}
