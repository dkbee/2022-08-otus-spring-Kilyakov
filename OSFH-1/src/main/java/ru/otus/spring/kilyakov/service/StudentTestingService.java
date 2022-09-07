package ru.otus.spring.kilyakov.service;

public interface StudentTestingService {
    default void printAllQuestions() {
        System.out.println("Test output");
    }
}
