package ru.otus.spring.kilyakov.service.shell.impl;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
import ru.otus.spring.kilyakov.service.CommentService;
import ru.otus.spring.kilyakov.service.shell.CommentShellService;

import java.sql.SQLException;
import java.util.List;

@ShellComponent
public class CommentShellServiceImpl implements CommentShellService {

    private final CommentService commentService;

    public CommentShellServiceImpl(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    @ShellMethod(value = "Insert comment", key = {"--insert --comment", "-i --comment"})
    public CommentDto insert(String comment, Long bookId) {
        return commentService.save(Comment.builder()
                .comment(comment)
                .book(Book.builder().id(bookId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Get comment by id", key = {"--get --comment", "-g --comment"})
    public CommentDto getById(Long id) {
        return commentService.getById(id);
    }

    @Override
    @ShellMethod(value = "Update comment", key = {"--update --comment", "-u --comment"})
    public CommentDto update(Long commentId, String comment, Long bookId) {
        return commentService.update(Comment.builder()
                .id(commentId)
                .comment(comment)
                .book(Book.builder().id(bookId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Delete comment", key = {"--delete --comment", "-d --comment"})
    public CommentDto delete(Long id) {
        return commentService.deleteById(id);
    }

    @ShellMethod(value = "Start console", key = {"-sc"})
    public void startConsole() throws SQLException {
        Console.main();
    }
}