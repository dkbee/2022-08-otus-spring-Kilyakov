package ru.otus.spring.kilyakov.domain;

import lombok.*;

@Getter
@Setter
public class Beer extends Drink{
    private int og;
    private int ibu;

    public Beer(String name, int  alcohol, int volume, int og, int ibu) {
        super(name, alcohol, volume);
        this.og = og;
        this.ibu = ibu;
    }
}
