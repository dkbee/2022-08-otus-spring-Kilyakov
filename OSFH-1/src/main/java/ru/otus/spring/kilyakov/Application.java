package ru.otus.spring.kilyakov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.kilyakov.service.impl.StudentTestingServiceImpl;

public class Application {
    public static void main(String... args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        StudentTestingServiceImpl testingService = context.getBean(StudentTestingServiceImpl.class);
        testingService.printAllQuestions();
    }
}
