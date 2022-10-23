package ru.otus.spring.kilyakov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {
    public static void main(String... args) {
        var ctx = SpringApplication.run(Application.class, args);
    }
}
