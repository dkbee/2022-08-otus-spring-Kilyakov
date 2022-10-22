package ru.otus.spring.kilyakov.dao;

import ru.otus.spring.kilyakov.domain.Question;

import java.util.List;

public interface CsvResourceDao {
    public List<Question> readAllQuestions() throws Exception;
}
