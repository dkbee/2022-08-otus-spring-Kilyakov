package ru.otus.spring.kilyakov.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.kilyakov.domain.*;

import java.util.Collection;

@MessagingGateway
public interface BarService {
    @Gateway(requestChannel = "barFlow.input", replyChannel = "barChannel")
    Collection<Drink> processDrink(Collection<OrderItem> orderItem);

}
