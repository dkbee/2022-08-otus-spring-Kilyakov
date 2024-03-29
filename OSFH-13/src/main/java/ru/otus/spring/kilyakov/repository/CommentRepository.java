package ru.otus.spring.kilyakov.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.kilyakov.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
