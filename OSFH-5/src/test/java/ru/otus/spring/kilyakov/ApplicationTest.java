package ru.otus.spring.kilyakov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.kilyakov.dao.AuthorDao;
import ru.otus.spring.kilyakov.dao.BookDao;
import ru.otus.spring.kilyakov.dao.GenreDao;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;

import java.util.List;

@SpringBootTest
class ApplicationTest {


    @Autowired
    BookDao bookDao;

    @Autowired
    GenreDao genreDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    public void insertBookTest() {
        Book expected = Book.builder().name("PlayBook")
                .author(Author.builder().id(1L).build())
                .genre(Genre.builder().id(2L).build())
                .build();
        bookDao.insert(expected);
        Book result = bookDao.getById(2L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected.getName(), result.getName());
    }

    @Test
    public void getBookTest() {
        Book result = bookDao.getById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Bro Code", result.getName());
    }

    @Test
    public void getAllBooksTest() {
        List<Book> result = bookDao.getAll();
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
        bookDao.update(expected);
        Book result = bookDao.getById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected.getName(), result.getName());
        Assertions.assertEquals(expected.getAuthor().getFirstName(), result.getAuthor().getFirstName());
    }

    @Test
    public void deleteBookTest() {
        bookDao.deleteById(1L);
        Book result = bookDao.getById(1L);
        Assertions.assertNull(result);
    }

}
