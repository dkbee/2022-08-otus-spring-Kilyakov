package ru.otus.spring.kilyakov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String middleName;
    @Column
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private List<Book> book;


    @Override
    public String toString() {
        return "Id = " + this.id + ", First name  = \"" + this.firstName + "\", Middle name = \"" + this.middleName
                + "\", Last name  = \"" + lastName + "\" ";
    }
}
