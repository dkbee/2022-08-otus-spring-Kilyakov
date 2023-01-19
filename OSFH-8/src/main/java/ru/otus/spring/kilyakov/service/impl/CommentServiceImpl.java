package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
import ru.otus.spring.kilyakov.exception.NotFoundException;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.CommentRepository;
import ru.otus.spring.kilyakov.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public CommentDto save(Comment comment) throws NotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(comment.getBook().getId());
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("Book with id: " + comment.getBook().getId() + " was not found");
        }
        Comment savedComment = commentRepository.save(comment);
        bookOptional.ifPresent(book -> {
            List<Comment> comments = book.getComments();
            if (comments == null) {
                comments = new ArrayList<>();
                book.setComments(comments);
            }
            comments.add(comment);
            bookRepository.save(book);
        });

        return getCommentDto(savedComment);
    }

    @Override
    public CommentDto update(Comment comment) {
        Optional<Book> bookOptional = bookRepository.findById(comment.getBook().getId());
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("Book with id:" + comment.getBook().getId() + " not found");
        }
        Comment updatedComment = commentRepository.save(comment);
        return getCommentDto(updatedComment);
    }

    @Override
    public CommentDto getById(String id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        Comment comment = commentOptional.orElse(null);
        return getCommentDto(comment);
    }

    @Override
    public List<CommentDto> findByBookId(String bookId) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByBookId(bookId);
        commentList.forEach(comment -> commentDtoList.add(getCommentDto(comment)));
        return commentDtoList;
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        commentOptional.ifPresent(comment -> {
            Optional<Book> bookOptional = bookRepository.findById(comment.getBook().getId());
            bookOptional.ifPresent(book -> {
                List<Comment> comments = book.getComments();
                if (comments != null) {
                    comments.removeIf(value -> value.getId().equals(id));
                    bookRepository.save(book);
                }
            });
        });
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByBookId(String bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        bookOptional.ifPresent(book -> {
            List<Comment> comments = book.getComments();
            if (comments != null) {
                book.setComments(null);
                bookRepository.save(book);
            }
        });
        commentRepository.deleteByBookId(bookId);
    }

    private static CommentDto getCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        if (comment != null) {
            commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .bookName(comment.getBook() != null && comment.getBook().getName() != null
                            ? comment.getBook().getName() : null)
                    .build();
        }
        return commentDto;
    }
}
