package ru.otus.spring.kilyakov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItem {

    public OrderItem(String name, Class<?> type) {
        this.name = name;
        this.type = type;
        this.volume = null;
        this.drinkType = null;
    }
    public OrderItem(String name, String drinkType, Class<?> type) {
        this.name = name;
        this.type = type;
        this.volume = null;
        this.drinkType = drinkType;
    }

    private final String name;
    private final Integer volume;
    private final Class<?> type;
    private final String drinkType;

}
