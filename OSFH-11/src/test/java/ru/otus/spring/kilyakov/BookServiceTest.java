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
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Import(value = {BookServiceImpl.class})
@EnableMongock
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Testcontainers
class BookServiceTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    AuthorRepository authorRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void insertBookTest() {
        Book expected = Book.builder()
                .id("2")
                .name("PlayBook")
                .author(Author.builder().id("1").build())
                .genre(Genre.builder().id("2").build())
                .build();
        bookService.save(expected);
        Optional<Book> actual = bookRepository.findById("2");
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected.getName(), actual.get().getName());
    }

    @Test
    public void getBookTest() {
        BookDto expectedBook = bookService.getById("1");
        Optional<Book> actual = bookRepository.findById("1");
        Assertions.assertNotNull(expectedBook);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expectedBook.getName(), actual.get().getName());
    }

    @Test
    public void getAllBooksTest() {
        List<BookDto> result = bookService.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() != 0);
    }

    @Test
    public void updateBookTest() {
        Author author = authorRepository.save(Author.builder().firstName("Anita").lastName("Appleby").build());
        Book expected = Book.builder()
                .id("1")
                .name("Of Course You're Still Single, Take a Look at Yourself You Dumb Slut.")
                .author(author)
                .genre(Genre.builder().id("2").build())
                .build();
        bookService.update(expected);
        Optional<Book> expectedBook = bookRepository.findById("1");
        Assertions.assertTrue(expectedBook.isPresent());
        Assertions.assertEquals(expected.getName(), expectedBook.get().getName());
        Assertions.assertEquals(expected.getAuthor().getFirstName(), expectedBook.get().getAuthor().getFirstName());
    }

    @Test
    public void deleteBookTest() {
        bookService.deleteById("1");
        Optional<Book> expectedBook = bookRepository.findById("1");
        Assertions.assertFalse(expectedBook.isPresent());
    }

}
