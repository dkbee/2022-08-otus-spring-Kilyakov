package ru.otus.spring.kilyakov.service.shell.impl;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
import ru.otus.spring.kilyakov.service.CommentService;
import ru.otus.spring.kilyakov.service.shell.CommentShellService;

import java.util.List;

@ShellComponent
public class CommentShellServiceImpl implements CommentShellService {

    private final CommentService commentService;

    public CommentShellServiceImpl(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    @ShellMethod(value = "Insert comment", key = {"--insert --comment", "-i --comment"})
    public CommentDto insert(String comment, String bookId) {
        return commentService.save(Comment.builder()
                .comment(comment)
                .book(Book.builder().id(bookId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Get comment by id", key = {"--get --comment", "-g --comment"})
    public CommentDto getById(String id) {
        return commentService.getById(id);
    }

    @Override
    @ShellMethod(value = "Get all comments by book", key = {"--all --comment", "-a --comment"})
    public List<CommentDto> findByBookId(String bookId) {
        return commentService.findByBookId(bookId);
    }

    @Override
    @ShellMethod(value = "Update comment", key = {"--update --comment", "-u --comment"})
    public CommentDto update(String commentId, String comment, String bookId) {
        return commentService.update(Comment.builder()
                .id(commentId)
                .comment(comment)
                .book(Book.builder().id(bookId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Delete comment", key = {"--delete --comment", "-d --comment"})
    public void delete(String id) {
        commentService.deleteById(id);
    }

    @Override
    @ShellMethod(value = "Delete comment", key = {"--delete --all --comment", "-d -a --comment"})
    public void deleteByBookId(String bookId) {
        commentService.deleteByBookId(bookId);
    }
}