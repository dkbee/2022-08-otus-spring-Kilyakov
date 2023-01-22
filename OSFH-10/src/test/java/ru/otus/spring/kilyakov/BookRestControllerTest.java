package ru.otus.spring.kilyakov;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.kilyakov.controller.rest.BookRestController;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookRestController.class)
class BookRestControllerTest {

    @MockBean
    BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        Author author = Author.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id(1L)
                .name("Story")
                .build();
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .name("Test")
                .author(author)
                .genre(genre)
                .build();
        List<BookDto> bookDtoList = new ArrayList<>();
        bookDtoList.add(bookDto);
        Mockito.when(bookService.getById(anyLong())).thenReturn(bookDto);
        Mockito.when(bookService.save(any())).thenReturn(bookDto);
        Mockito.when(bookService.update(any())).thenReturn(bookDto);
        Mockito.when(bookService.getAll()).thenReturn(bookDtoList);
    }

    @Test
    public void insertBookTest() throws Exception {
        Author author = Author.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id(1L)
                .name("Story")
                .build();
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .name("Test")
                .author(author)
                .genre(genre)
                .build();
        mockMvc.perform(post("/book")
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(bookService, times(1)).save(bookDto.toDomainObject());
    }

    @Test
    public void getBookTest() throws Exception {
        Author author = Author.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id(1L)
                .name("Story")
                .build();
        BookDto excepted = BookDto.builder()
                .id(1L)
                .name("Test")
                .author(author)
                .genre(genre)
                .build();
        MvcResult mvcResult = mockMvc.perform(get("/book/1"))
                .andReturn();
        String resultString = mvcResult.getResponse().getContentAsString();
        BookDto actual = objectMapper.readValue(resultString, BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(excepted.getName(), actual.getName());
    }

    @Test
    public void getAllBooksTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/book"))
                .andReturn();
        String resultString = mvcResult.getResponse().getContentAsString();
        List<BookDto> bookDtoList = objectMapper.readValue(resultString, new TypeReference<List<BookDto>>() {
        });
        Assertions.assertEquals(1, bookDtoList.size());
    }

    @Test
    public void updateBookTest() throws Exception {
        Author author = Author.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        Genre genre = Genre.builder()
                .id(1L)
                .name("Story")
                .build();
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .name("Test")
                .author(author)
                .genre(genre)
                .build();
        mockMvc.perform(put("/book")
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(bookService, times(1)).update(bookDto.toDomainObject());
    }

    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(delete("/book/1"))
                .andReturn();
        verify(bookService, times(1)).deleteById(1L);
    }
}
