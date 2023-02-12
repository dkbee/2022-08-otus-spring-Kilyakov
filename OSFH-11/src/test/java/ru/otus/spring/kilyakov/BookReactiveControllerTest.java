package ru.otus.spring.kilyakov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kilyakov.controller.reactive.BookReactiveController;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebFluxTest(BookReactiveController.class)
class BookReactiveControllerTest {

    @MockBean
    BookRepository bookRepository;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    public void beforeEach() {
        Author author = Author.builder()
                .id("1")
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id("1")
                .name("Story")
                .build();
        Book bookDto = Book.builder()
                .id("1")
                .name("Test")
                .author(author)
                .genre(genre)
                .build();
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookDto);
        when(bookRepository.findById(anyString())).thenReturn(Mono.just(bookDto));
        when(bookRepository.save(any())).thenReturn(Mono.just(bookDto));
        when(bookRepository.findAll()).thenReturn(
                Flux.fromIterable(bookList));
        when(bookRepository.deleteById(anyString())).thenReturn(ServerResponse.ok().build().then());
    }
    @Test
    public void insertBookTest() {
        Author author = Author.builder()
                .id("1")
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id("1")
                .name("Story")
                .build();
        BookDto bookDto = BookDto.builder()
                .id("1")
                .name("Test")
                .author(author)
                .genre(genre)
                .build();

        webClient.post()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bookDto))
                .exchange()
                .expectStatus()
                .isOk();
        verify(bookRepository, times(1)).save(bookDto.toDomainObject());
    }

    @Test
    public void getBookTest() {
        Author author = Author.builder()
                .id("1")
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id("1")
                .name("Story")
                .build();
        BookDto excepted = BookDto.builder()
                .id("1")
                .name("Test")
                .author(author)
                .genre(genre)
                .build();

        var result = webClient.get()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class);

        BookDto actual = result.getResponseBody().blockFirst();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(excepted.getName(), actual.getName());
    }

    @Test
    public void getAllBooksTest() {

        var result = webClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class);
        List<BookDto> actual = result.getResponseBody().collectList().block();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    public void updateBookTest() {
        Author author = Author.builder()
                .id("1")
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id("1")
                .name("Story")
                .build();
        BookDto bookDto = BookDto.builder()
                .id("1")
                .name("Test")
                .author(author)
                .genre(genre)
                .build();
        webClient.put()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bookDto))
                .exchange()
                .expectStatus()
                .isOk();
        verify(bookRepository, times(1)).save(bookDto.toDomainObject());
    }

    @Test
    public void deleteBookTest() {
        webClient.delete().uri("/book/1").exchange().expectStatus().isOk();
        verify(bookRepository, times(1)).deleteById("1");
    }
}