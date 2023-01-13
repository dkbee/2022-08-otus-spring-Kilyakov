package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto save(Comment comment);

    CommentDto update(Comment comment);

    CommentDto getById(String id);

    List<CommentDto> findByBookId(String bookId);

    void deleteById(String id);

    void deleteByBookId(String bookId);
}
