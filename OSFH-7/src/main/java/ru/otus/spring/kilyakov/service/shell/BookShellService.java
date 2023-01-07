package ru.otus.spring.kilyakov.service.shell;

import ru.otus.spring.kilyakov.dto.BookDto;

import java.util.List;

public interface BookShellService {

    public BookDto insert(String name, Long authorId, Long bookId);

    public BookDto getById(Long id);

    public List<BookDto> getAll();

    public BookDto update(Long bookId, String name, Long authorId, Long genreId);

    public void delete(Long id);
}
