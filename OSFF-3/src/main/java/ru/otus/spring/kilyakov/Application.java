package ru.otus.spring.kilyakov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.otus.spring.kilyakov.service.impl.TestStarterImpl;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {
    public static void main(String... args) {
        var ctx = SpringApplication.run(Application.class, args);
        TestStarterImpl testingService = ctx.getBean(TestStarterImpl.class);
        testingService.run();
    }
}
