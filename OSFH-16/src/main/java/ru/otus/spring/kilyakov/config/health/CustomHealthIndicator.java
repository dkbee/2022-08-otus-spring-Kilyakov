package ru.otus.spring.kilyakov.config.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

    CustomHealthIndicator() {
        super("Custom health check failed");
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        double chance = ThreadLocalRandom.current().nextDouble();
        builder.status(new Status("0", "All is OK"));
        if (chance > 0.9) {
            builder.down();
        }
    }
}
