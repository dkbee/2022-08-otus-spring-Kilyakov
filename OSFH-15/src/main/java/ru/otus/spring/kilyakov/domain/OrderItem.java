package ru.otus.spring.kilyakov.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItem {

    private final String name;
    private final int volume;
    private final Class<?> type;

}
