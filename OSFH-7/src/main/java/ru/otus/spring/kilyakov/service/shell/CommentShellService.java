package ru.otus.spring.kilyakov.service.shell;

import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;

import java.util.List;

public interface CommentShellService {

    public CommentDto insert(String comment, Long bookId);

    public CommentDto getById(Long id);

    public List<CommentDto> getAll(Long bookId);

    public CommentDto update(Long commentId, String comment, Long bookId);

    public void delete(Long id);

    public void deleteAll(Long bookId);
}
