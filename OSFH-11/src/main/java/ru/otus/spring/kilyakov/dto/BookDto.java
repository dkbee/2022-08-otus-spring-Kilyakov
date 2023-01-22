package ru.otus.spring.kilyakov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Comment;
import ru.otus.spring.kilyakov.domain.Genre;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;
    private String name;
    private Author author;
    private Genre genre;
    private List<Comment> comments;

    public Book toDomainObject(){
        return new Book(id, name, author, genre, comments);
    }

    public static BookDto toDto(Book book){
        return new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getGenre(), book.getComments());
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String stringComments = "";
        if (comments != null) {
            comments.forEach(comment -> {
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append("\"");
                        stringBuilder.append(comment.getComment());
                        stringBuilder.append("\"");
                    }
            );
            stringComments = ", Comments: [" + stringBuilder + "]";
        }
        String authorString = author != null ? author.toString() : "";
        String genreString = genre != null ? genre.toString() : "";
        return "Id = " + this.id + ", Name = \"" + this.name + "\", Author = { " + authorString + " }, Genre = { "
                + genreString + " }" + stringComments;
    }
}
