package ru.otus.spring.kilyakov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transformer.GenericTransformer;
import ru.otus.spring.kilyakov.domain.Cocktail;
import ru.otus.spring.kilyakov.domain.Drink;
import ru.otus.spring.kilyakov.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class IntegrationConfig {

    @Bean
    public QueueChannel ordersChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel barChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }

    @Bean
    public IntegrationFlow barFlow() {
        return f -> f.split()
                .<OrderItem, Class<?>>route(
                        OrderItem::getType,
                        mapping -> mapping
                                .subFlowMapping(Drink.class, sf ->
                                        sf.handle("puzzlesBarService", "pourDrink")
                                                .aggregate()
                                                .transform((GenericTransformer< List<Optional<Drink>>, List<Drink>>) source -> {
                                                    List<Drink> drinks = new ArrayList<>();
                                                    for (Optional<Drink> optional:
                                                    source) {
                                                        drinks.add(optional.orElse(null));
                                                    }
                                                    return drinks;
                                                })
                                                .channel("barChannel")
                                )
                                .subFlowMapping(Cocktail.class, sf ->
                                        sf.handle("puzzlesBarService", "pourCocktail")
                                                .aggregate()
                                                .transform((GenericTransformer< List<Optional<Cocktail>>, List<Cocktail>>) source -> {
                                                    List<Cocktail> drinks = new ArrayList<>();
                                                    for (Optional<Cocktail> optional:
                                                            source) {
                                                        drinks.add(optional.orElse(null));
                                                    }
                                                    return drinks;
                                                })
                                                .channel("barChannel")
                                )
                );
    }
}
