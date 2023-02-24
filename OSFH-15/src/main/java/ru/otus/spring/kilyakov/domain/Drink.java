package ru.otus.spring.kilyakov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Drink {
    private String name;
    private int alcohol;
    private int volume;
}
