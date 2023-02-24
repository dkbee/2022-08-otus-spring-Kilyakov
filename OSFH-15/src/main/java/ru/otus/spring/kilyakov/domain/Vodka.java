package ru.otus.spring.kilyakov.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vodka extends Drink{
    public Vodka(String name, int volume) {
        super(name, 40, volume);
    }
}
