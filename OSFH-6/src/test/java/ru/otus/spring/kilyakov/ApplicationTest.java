package ru.otus.spring.kilyakov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.GenreRepository;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ApplicationTest {


    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreDao;

    @Autowired
    AuthorRepository authorDao;

    @Test
    public void insertBookTest() {
        Book expected = Book.builder().name("PlayBook")
                .author(Author.builder().id(1L).build())
                .genre(Genre.builder().id(2L).build())
                .build();
        bookRepository.save(expected);
        Optional<Book> result = bookRepository.getById(2L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected.getName(), result.get().getName());
    }

    @Test
    public void getBookTest() {
        Optional<Book> result = bookRepository.getById(1L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Bro Code", result.get().getName());
    }

    @Test
    public void getAllBooksTest() {
        List<Book> result = bookRepository.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() != 0);
    }

    @Test
    public void updateBookTest() {
        authorDao.insert(Author.builder().firstName("Anita").lastName("Appleby").build());
        Book expected = Book.builder()
                .id(1L)
                .name("Of Course You're Still Single, Take a Look at Yourself You Dumb Slut.")
                .author(Author.builder().id(3L).firstName("Anita").lastName("Appleby").build())
                .genre(Genre.builder().id(2L).build())
                .build();
        bookRepository.update(expected);
        Optional<Book> result = bookRepository.getById(1L);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected.getName(), result.get().getName());
        Assertions.assertEquals(expected.getAuthor().getFirstName(), result.get().getAuthor().getFirstName());
    }

    @Test
    public void deleteBookTest() {
        bookRepository.deleteById(1L);
        Optional<Book> result = bookRepository.getById(1L);
        Assertions.assertNull(result);
    }

}
