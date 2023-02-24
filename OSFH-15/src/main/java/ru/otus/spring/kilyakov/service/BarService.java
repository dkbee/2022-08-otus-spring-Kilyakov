package ru.otus.spring.kilyakov.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.kilyakov.domain.*;

import java.util.Collection;

@MessagingGateway
public interface BarService {
    @Gateway(requestChannel = "barFlow.input", replyChannel = "barChannel")
    Collection<Beer> processBeer(Collection<OrderItem> orderItem);

    @Gateway(requestChannel = "barFlow.input", replyChannel = "barChannel")
    Collection<Vodka> processVodka(Collection<OrderItem> orderItem);

    @Gateway(requestChannel = "barFlow.input", replyChannel = "barChannel")
    Collection<Cocktail> processCocktail(Collection<OrderItem> orderItem);
}
