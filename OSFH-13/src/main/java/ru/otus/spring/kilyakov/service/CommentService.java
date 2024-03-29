package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto save(Comment comment);

    CommentDto update(Comment comment);

    CommentDto getById(Long id);

    List<CommentDto> findByBookId(Long bookId);

    void deleteById(Long id);

    void deleteByBookId(Long bookId);
}
