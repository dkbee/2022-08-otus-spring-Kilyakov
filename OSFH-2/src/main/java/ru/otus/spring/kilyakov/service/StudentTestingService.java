package ru.otus.spring.kilyakov.service;

import ru.otus.spring.kilyakov.domain.Question;

import java.util.List;

public interface StudentTestingService {
    default void printAllQuestions() {
        System.out.println("Test output");
    }
    List<Question> getQuestions();
    void executeTest();
}
