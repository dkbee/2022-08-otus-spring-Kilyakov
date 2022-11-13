package ru.otus.spring.kilyakov.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Book {
    private final Long id;
    private final String name;
    private final Author author;
    private final Genre genre;

    @Override
    public String toString() {
        return "Id = " + this.id + ", Name = \"" + this.name + "\", Author = " + author.toString() + ", Genre = "
                + genre.toString() + " ";
    }
}
