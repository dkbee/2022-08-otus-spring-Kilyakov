package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
import ru.otus.spring.kilyakov.repository.CommentRepository;
import ru.otus.spring.kilyakov.service.CommentService;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    private static CommentDto getCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        if (comment != null) {
            commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .bookName(comment.getBook().getName())
                    .build();
        }
        return commentDto;
    }
}
