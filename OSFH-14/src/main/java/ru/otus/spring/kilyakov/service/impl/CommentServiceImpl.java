package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.relational.Book;
import ru.otus.spring.kilyakov.domain.relational.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
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

    @Override
    public CommentDto save(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return getCommentDto(savedComment);
    }

    @Transactional
    @Override
    public CommentDto update(Comment comment) {
        Comment updatedComment = commentRepository.save(comment);
        return getCommentDto(updatedComment);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto getById(Long id) {
        Optional<Comment> bookOptional = commentRepository.findById(id);
        Comment comment = bookOptional.orElse(null);
        return getCommentDto(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        List<Comment> commentList = new ArrayList<>();
        if (book.isPresent()) {
            commentList = book.get().getComments();
        }
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentList.forEach(comment -> commentDtoList.add(CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .build()));
        return commentDtoList;
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.ifPresent(value -> {
            if (value.getComments() != null && value.getComments().size() > 0) {
                value.getComments().forEach(comment -> comment.setBook(null));
                value.getComments().clear();
            }
        });
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
