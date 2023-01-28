package ru.otus.spring.kilyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.kilyakov.controller.BookController;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Genre;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.service.AuthorService;
import ru.otus.spring.kilyakov.service.BookService;
import ru.otus.spring.kilyakov.service.GenreService;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @MockBean
    BookService bookService;

    @MockBean
    AuthorService authorService;

    @MockBean
    GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DataSource dataSource;

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
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre);
        Mockito.when(authorService.getAll()).thenReturn(authorList);
        Mockito.when(genreService.getAll()).thenReturn(genreList);
        Mockito.when(bookService.getById(anyLong())).thenReturn(bookDto);
        Mockito.when(bookService.getAll()).thenReturn(bookDtoList);
    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    public void insertBookViewTest() throws Exception {
        mockMvc.perform(get("/book/add"))
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andReturn();
    }

    @Test
    public void insertBookDenied() throws Exception {
        mockMvc.perform(post("/book/add")
                        .param("name", "Test")
                        .param("id", "1")
                        .param("author.id", "1")
                        .param("author.firstName", "Ivan")
                        .param("author.LastName", "Ivanov")
                        .param("genre.id", "1")
                        .param("genre.name", "Story"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @WithMockUser(
            username = "admin"
    )
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
        mockMvc.perform(post("/book/add")
                        .param("name", "Test")
                        .param("id", "1")
                        .param("author.id", "1")
                        .param("author.firstName", "Ivan")
                        .param("author.LastName", "Ivanov")
                        .param("genre.id", "1")
                        .param("genre.name", "Story")
                )
                .andExpect(view().name("redirect:/book/"))
                .andReturn();
        verify(bookService, times(1)).save(bookDto.toDomainObject());
    }

    @Test
    public void getBooksDeniedTest() throws Exception {
        mockMvc.perform(get("/book/")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    public void getBookTest() throws Exception {
        mockMvc.perform(get("/book/get")
                        .param("id", "1"))
                .andExpect(view().name("read"))
                .andExpect(model().attributeExists("book"))
                .andReturn();
    }

    @Test
    public void getAllBooksDeniedTest() throws Exception {
        mockMvc.perform(get("/book/"))
                .andExpect(status().is3xxRedirection());

    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    public void getAllBooksTest() throws Exception {
        mockMvc.perform(get("/book/"))
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("books"))
                .andReturn();
    }

    @Test
    public void updateBookViewDeniedTest() throws Exception {
        mockMvc.perform(get("/book/edit")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    public void updateBookViewTest() throws Exception {
        mockMvc.perform(get("/book/edit")
                        .param("id", "1"))
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andReturn();
    }

    @Test
    public void updateBookDeniedTest() throws Exception {
        mockMvc.perform(post("/book/edit")
                        .param("name", "Test")
                        .param("id", "1")
                        .param("author.id", "1")
                        .param("author.firstName", "Ivan")
                        .param("author.LastName", "Ivanov")
                        .param("genre.id", "1")
                        .param("genre.name", "Story")
                )
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(
            username = "admin"
    )
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
        mockMvc.perform(post("/book/edit")
                        .param("name", "Test")
                        .param("id", "1")
                        .param("author.id", "1")
                        .param("author.firstName", "Ivan")
                        .param("author.LastName", "Ivanov")
                        .param("genre.id", "1")
                        .param("genre.name", "Story")
                )
                .andExpect(view().name("redirect:/book/"))
                .andReturn();
        verify(bookService, times(1)).update(bookDto.toDomainObject());
    }

    @Test
    public void deleteBookDeniedTest() throws Exception {
        mockMvc.perform(post("/book/delete")
                        .param("id", "1")
                )
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(post("/book/delete")
                        .param("id", "1")
                )
                .andExpect(view().name("redirect:/book/"))
                .andReturn();
        verify(bookService, times(1)).deleteById(1L);
    }
}
