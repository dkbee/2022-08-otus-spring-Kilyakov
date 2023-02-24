package ru.otus.spring.kilyakov.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Beer {
    private String name;
    private int og;
    private int ibu;
    private int volume;
}
