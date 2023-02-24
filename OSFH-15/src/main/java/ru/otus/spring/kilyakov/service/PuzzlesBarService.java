package ru.otus.spring.kilyakov.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.kilyakov.domain.Beer;
import ru.otus.spring.kilyakov.domain.OrderItem;
import ru.otus.spring.kilyakov.domain.Vodka;

@Service
public class PuzzlesBarService {

    public Beer pourBeer(OrderItem orderItem) {
        return new Beer(orderItem.getName(), 1, orderItem.getVolume(), 1, 1);
    }

    public Vodka pourVodka(OrderItem orderItem) {
        return new Vodka(orderItem.getName(), orderItem.getVolume());
    }

}
