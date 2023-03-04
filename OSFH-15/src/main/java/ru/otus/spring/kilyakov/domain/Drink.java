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

    public Drink(String type, int volume) {
        this.type = type;
        this.volume = volume;
    }
    private String name;
    private String type;
    private double alcohol;
    private int volume;
}
