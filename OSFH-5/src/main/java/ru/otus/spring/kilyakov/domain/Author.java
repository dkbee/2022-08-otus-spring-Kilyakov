package ru.otus.spring.kilyakov.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Author {
    private final Long id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
}
