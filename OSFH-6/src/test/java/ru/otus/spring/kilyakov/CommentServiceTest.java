package ru.otus.spring.kilyakov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.repository.CommentRepository;
import ru.otus.spring.kilyakov.repository.impl.CommentRepositoryJpa;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentRepositoryJpa.class})
class CommentServiceTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void insertCommentTest() {
        Comment expected = Comment.builder().comment("PlayBook rulezzz")
                .book(Book.builder().id(1L).build())
                .build();
        commentRepository.save(expected);
        Comment actual = em.find(Comment.class, 2L);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getComment(), actual.getComment());
    }

    @Test
    public void getCommentTest() {
        Optional<Comment> actual = commentRepository.getById(1L);
        Comment expectedBook = em.find(Comment.class, 1L);
        Assertions.assertNotNull(expectedBook);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expectedBook.getComment(), actual.get().getComment());
        assertThat(actual).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    public void getAllCommentsTest() {
        List<Comment> actual = commentRepository.getAll(1L);
        TypedQuery<Comment> query = em.getEntityManager().createQuery("select c from Comment c " +
                        "where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", 1L);
        List<Comment> expectedComments = query.getResultList();
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(expectedComments);
        Assertions.assertEquals(actual.size(), expectedComments.size());
    }

    @Test
    public void updateCommentTest() {
        Comment expected = Comment.builder()
                .id(1L)
                .comment("Cool book")
                .build();
        commentRepository.update(expected);
        Comment expectedBook = em.find(Comment.class, 1L);
        Assertions.assertNotNull(expectedBook);
        Assertions.assertEquals(expected.getComment(), expectedBook.getComment());
    }

    @Test
    public void deleteCommentTest() {
        commentRepository.deleteById(1L);
        Comment expectedBook = em.find(Comment.class, 1L);
        Assertions.assertNull(expectedBook);
    }

}
