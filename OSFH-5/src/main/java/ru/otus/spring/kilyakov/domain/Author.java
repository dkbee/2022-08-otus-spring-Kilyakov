package ru.otus.spring.kilyakov.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class Author {
    private final Long id;
    private final String firstName;
    private final String middleName;
    private final String lastName;


    @Override
    public String toString() {
        return "Id = " + this.id + ", First name  = \"" + this.firstName + "\", Middle name = \"" + this.middleName
                + "\", Last name  = \"" + lastName + "\" ";
    }
}
