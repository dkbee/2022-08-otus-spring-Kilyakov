package ru.otus.spring.kilyakov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;
    @ManyToOne(fetch = FetchType.EAGER)
    private Genre genre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        comments.forEach(comment -> {
                    stringBuilder.append("\"");
                    stringBuilder.append(comment.getComment());
                    stringBuilder.append("\", ");
                }
        );
        if (stringBuilder.length() > 2) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        return "Id = " + this.id + ", Name = \"" + this.name + "\", Author = " + author.toString() + ", Genre = "
                + genre.toString() + "\", Comments: [" + stringBuilder + "] ";
    }
}
