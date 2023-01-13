package ru.otus.spring.kilyakov.service.shell;

import ru.otus.spring.kilyakov.dto.BookDto;

import java.util.List;

public interface BookShellService {

    public BookDto insert(String name, String authorId, String bookId);

    public BookDto getById(String id);

    public List<BookDto> getAll();

    public BookDto update(String bookId, String name, String authorId, String genreId);

    public void delete(String id);
}
