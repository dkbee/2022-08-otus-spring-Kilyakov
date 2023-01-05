package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
import ru.otus.spring.kilyakov.repository.CommentRepository;
import ru.otus.spring.kilyakov.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public CommentDto save(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return getCommentDto(savedComment);
    }

    @Transactional
    @Override
    public CommentDto update(Comment comment) {
        Comment updatedComment = commentRepository.update(comment);
        return getCommentDto(updatedComment);
    }

    @Transactional
    @Override
    public CommentDto getById(Long id) {
        Optional<Comment> bookOptional = commentRepository.getById(id);
        Comment comment = bookOptional.orElse(null);
        return getCommentDto(comment);
    }

    @Transactional
    @Override
    public List<CommentDto> getAll(Long bookId) {
        List<Comment> comments = commentRepository.getAll(bookId);
        List<CommentDto> bookDtoList = new ArrayList<>();
        comments.forEach(comment -> bookDtoList.add(CommentDto.builder()
                .name(comment.getComment())
                .build()));
        return bookDtoList;
    }

    @Transactional
    @Override
    public CommentDto deleteById(Long id) {
        Comment comment = commentRepository.deleteById(id);
        return getCommentDto(comment);
    }

    private static CommentDto getCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        if (comment != null) {
            commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .comments(comment.getBook().getComments())
                    .build();
        }
        return commentDto;
    }
}
