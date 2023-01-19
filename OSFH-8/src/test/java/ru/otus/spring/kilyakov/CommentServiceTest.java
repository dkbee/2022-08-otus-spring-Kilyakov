package ru.otus.spring.kilyakov;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.dto.CommentDto;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.CommentRepository;
import ru.otus.spring.kilyakov.service.CommentService;
import ru.otus.spring.kilyakov.service.impl.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Import(value = CommentServiceImpl.class)
@EnableMongock
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Testcontainers
class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BookRepository bookRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void insertCommentTest() {
        Comment expected = Comment.builder()
                .id("2")
                .comment("PlayBook rulezzz")
                .book(Book.builder().id("1").build())
                .build();
        commentService.save(expected);
        Optional<Comment> actual = commentRepository.findById("2");
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected.getComment(), actual.get().getComment());
    }

    @Test
    public void getCommentTest() {
        CommentDto expectedComment = commentService.getById("1");
        Optional<Comment> actual = commentRepository.findById("1");
        Assertions.assertNotNull(expectedComment);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expectedComment.getComment(), actual.get().getComment());
    }

    @Test
    public void getAllCommentsTest() {
        List<CommentDto> expectedComments = commentService.findByBookId("1");
        Optional<Book> book = bookRepository.findById("1");
        List<Comment> actual = new ArrayList<>();
        if (book.isPresent()) {
            actual = book.get().getComments();
        }
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(expectedComments);
        Assertions.assertEquals(actual.size(), expectedComments.size());
    }

    @Test
    public void updateCommentTest() {
        Comment expected = Comment.builder()
                .id("1")
                .comment("Cool book")
                .book(Book.builder()
                        .id("1")
                        .build())
                .build();
        commentService.update(expected);
        Optional<Comment> expectedComment = commentRepository.findById("1");
        Assertions.assertTrue(expectedComment.isPresent());
        Assertions.assertEquals(expected.getComment(), expectedComment.get().getComment());
    }

    @Test
    public void deleteCommentTest() {
        commentService.deleteById("1");
        Optional<Comment> expectedComment = commentRepository.findById("1");
        Assertions.assertFalse(expectedComment.isPresent());
    }

    @Test
    public void deleteAllCommentsForBookTest() {
        commentService.deleteByBookId("1");
        Optional<Book> expectedBook = bookRepository.findById("1");
        Assertions.assertTrue(expectedBook.isPresent());
        Assertions.assertTrue(expectedBook.get().getComments() == null
                || expectedBook.get().getComments().size() == 0);
    }

}
