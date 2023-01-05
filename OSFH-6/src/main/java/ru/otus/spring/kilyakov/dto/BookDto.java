package ru.otus.spring.kilyakov.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.domain.Genre;

import java.util.List;

@Data
@Builder
public class BookDto {

    private Long id;
    private String name;
    private Author author;
    private Genre genre;
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
