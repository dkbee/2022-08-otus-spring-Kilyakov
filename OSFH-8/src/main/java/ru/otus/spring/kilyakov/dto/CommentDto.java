package ru.otus.spring.kilyakov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String id;

    private String comment;

    private String bookName;

    @Override
    public String toString() {
        String bookString = "";
        if (bookName != null) {
            bookString = ", Book: \"" + bookName + "\"";
        }
        return "Id = " + this.id + ", Comment  = \"" + this.comment + "\"" + bookString;
    }
}
