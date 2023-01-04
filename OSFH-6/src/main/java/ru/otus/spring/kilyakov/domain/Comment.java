package ru.otus.spring.kilyakov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    private Long id;
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Book book;

    @Override
    public String toString() {
        return "Id = " + this.id + ", Comment  = \"" + this.comment + "\" ";
    }
}