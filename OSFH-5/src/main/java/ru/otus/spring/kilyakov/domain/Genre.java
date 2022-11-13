package ru.otus.spring.kilyakov.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class Genre {
    private final Long id;
    private final String name;

    @Override
    public String toString() {
        return "Id = " + this.id + ", Genre  = \"" + this.name + " ";
    }
}
