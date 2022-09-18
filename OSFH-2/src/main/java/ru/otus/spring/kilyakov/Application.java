package ru.otus.spring.kilyakov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.kilyakov.service.impl.TestStarterImpl;

@Configuration
@ComponentScan
public class Application {
    public static void main(String... args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);
        TestStarterImpl testingService = context.getBean(TestStarterImpl.class);
        testingService.run();
    }
}
