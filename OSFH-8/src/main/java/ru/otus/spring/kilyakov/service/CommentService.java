package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
import ru.otus.spring.kilyakov.exception.NotFoundException;

import java.util.List;

public interface CommentService {
    CommentDto save(Comment comment) throws NotFoundException;

    CommentDto update(Comment comment);

    CommentDto getById(String id);

    List<CommentDto> findByBookId(String bookId);

    void deleteById(String id);

    void deleteByBookId(String bookId);
}
