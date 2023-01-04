package ru.otus.spring.kilyakov.service.impl;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.dao.CommentRepository;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.service.CommentShellService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class CommentShellServiceImpl implements CommentShellService {

    private final CommentRepository commentRepository;

    public CommentShellServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    @ShellMethod(value = "Insert comment", key = {"--insert --comment", "-i --comment"})
    public String insert(String comment, Long bookId) {
        return "Count of books inserted: " + commentRepository.save(Comment.builder()
                .comment(comment)
                .book(Book.builder().id(bookId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Get comment by id", key = {"--get --comment", "-g --comment"})
    public Comment getById(Long id) {
        Optional<Comment> comment = commentRepository.getById(id);
        return comment.orElse(null);
    }

    @Override
    @ShellMethod(value = "Get all books", key = {"--all --comment", "-a --comment"})
    public List<Comment> getAll() {
        List<Comment> books = commentRepository.getAll();
        return books;
    }

    @Override
    @ShellMethod(value = "Update comment", key = {"--update --comment", "-u --comment"})
    public String update(Long commentId, String comment, Long bookId) {
        return "Count of books updated: " + commentRepository.update(Comment.builder()
                .id(commentId)
                .comment(comment)
                .book(Book.builder().id(bookId).build())
                .build());
    }

    @Override
    @ShellMethod(value = "Delete comment", key = {"--delete --comment", "-d --comment"})
    public String delete(Long id) {
        return "Count of books deleted: " + commentRepository.deleteById(id);
    }

    @ShellMethod(value = "Start console", key = {"-sc"})
    public void startConsole() throws SQLException {
        Console.main();
    }
}