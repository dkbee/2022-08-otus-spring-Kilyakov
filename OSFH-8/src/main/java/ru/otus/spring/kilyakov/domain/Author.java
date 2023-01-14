package ru.otus.spring.kilyakov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    private String id;

    private String firstName;

    private String middleName;

    private String lastName;

    @DBRef
    private List<Book> book;

    @Override
    public String toString() {
        return "Id = " + this.id + ", First name  = \"" + this.firstName + "\", Middle name = \"" + this.middleName
                + "\", Last name  = \"" + lastName + "\" ";
    }
}
