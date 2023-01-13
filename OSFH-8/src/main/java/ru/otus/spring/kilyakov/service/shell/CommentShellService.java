package ru.otus.spring.kilyakov.service.shell;

import ru.otus.spring.kilyakov.dto.CommentDto;

import java.util.List;

public interface CommentShellService {

    public CommentDto insert(String comment, String bookId);

    public CommentDto getById(String id);

    public List<CommentDto> findByBookId(String bookId);

    public CommentDto update(String commentId, String comment, String bookId);

    public void delete(String id);

    public void deleteByBookId(String bookId);
}
