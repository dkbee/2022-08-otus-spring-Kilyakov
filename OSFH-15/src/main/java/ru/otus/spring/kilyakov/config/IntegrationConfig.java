package ru.otus.spring.kilyakov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.kilyakov.domain.Beer;
import ru.otus.spring.kilyakov.domain.OrderItem;
import ru.otus.spring.kilyakov.domain.Vodka;

@Configuration
public class IntegrationConfig {

    @Bean
    public QueueChannel ordersChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel barChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return new PollerMetadata();
    }

    @Bean
    public IntegrationFlow barFlow() {
        return f -> f.split()
                .<OrderItem, Class<?>>route(
                        OrderItem::getType,
                        mapping -> mapping
                                .subFlowMapping(Beer.class, sf ->
                                        sf.handle("puzzlesBarService", "pourBeer")
                                                .aggregate()
                                                .channel("barChannel")
                                )
                                .subFlowMapping(Vodka.class, sf ->
                                        sf.handle("puzzlesBarService", "pourVodka")
                                                .aggregate()
                                                .channel("barChannel")
                                )
                );
    }
}
