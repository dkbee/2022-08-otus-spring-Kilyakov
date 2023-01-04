package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Comment;

import java.util.List;

public interface CommentShellService {

    public String insert(String comment, Long bookId);

    public Comment getById(Long id);

    public List<Comment> getAll();

    public String update(Long commentId, String comment, Long bookId);

    public String delete(Long id);
}
