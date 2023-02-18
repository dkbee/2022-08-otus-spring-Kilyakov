package ru.otus.spring.kilyakov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.kilyakov.domain.relational.Author;
import ru.otus.spring.kilyakov.domain.relational.Book;
import ru.otus.spring.kilyakov.domain.relational.Genre;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookServiceTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void insertBookTest() {
        Book expected = Book.builder().name("PlayBook")
                .author(Author.builder().id(1L).build())
                .genre(Genre.builder().id(2L).build())
                .build();
        bookRepository.save(expected);
        Book actual = em.find(Book.class, 2L);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void getBookTest() {
        Optional<Book> actual = bookRepository.findById(1L);
        Book expectedBook = em.find(Book.class, 1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertNotNull(expectedBook);
        Assertions.assertEquals(expectedBook.getName(), actual.get().getName());
        assertThat(actual).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    public void getAllBooksTest() {
        List<Book> result = bookRepository.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() != 0);
    }

    @Test
    public void updateBookTest() {
        Author author = authorRepository.save(Author.builder().firstName("Anita").lastName("Appleby").build());
        Book expected = Book.builder()
                .id(1L)
                .name("Of Course You're Still Single, Take a Look at Yourself You Dumb Slut.")
                .author(author)
                .genre(Genre.builder().id(2L).build())
                .build();
        bookRepository.save(expected);
        Book expectedBook = em.find(Book.class, 1L);
        Assertions.assertNotNull(expectedBook);
        Assertions.assertEquals(expected.getName(), expectedBook.getName());
        Assertions.assertEquals(expected.getAuthor().getFirstName(), expectedBook.getAuthor().getFirstName());
    }

    @Test
    public void deleteBookTest() {
        bookRepository.deleteById(1L);
        Book expectedBook = em.find(Book.class, 1L);
        Assertions.assertNull(expectedBook);
    }

}
