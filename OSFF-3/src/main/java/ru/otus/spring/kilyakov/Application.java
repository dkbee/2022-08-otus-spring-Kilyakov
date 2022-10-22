package ru.otus.spring.kilyakov;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.kilyakov.service.impl.TestStarterImpl;

@SpringBootApplication
public class Application {
    public static void main(String... args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);
        TestStarterImpl testingService = context.getBean(TestStarterImpl.class);
        testingService.run();
    }
}
