package ru.otus.spring.kilyakov.dao.impl;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.stereotype.Component;
import ru.otus.spring.kilyakov.domain.Question;
import ru.otus.spring.kilyakov.dao.CsvResourceDao;

import java.util.ArrayList;
import java.util.List;

@Component
public class CsvResourceDaoImpl implements CsvResourceDao {

    private final FlatFileItemReader<Question> cvsFileItemReader;

    public CsvResourceDaoImpl(FlatFileItemReader<Question> cvsFileItemReader) {
        this.cvsFileItemReader = cvsFileItemReader;
    }

    @Override
    public List<Question> readAllQuestions() throws Exception {
        cvsFileItemReader.open(new ExecutionContext());
        List<Question> questions = new ArrayList<>();
        while (true) {
            Question question = cvsFileItemReader.read();
            if (question == null) {
                cvsFileItemReader.close();
                break;
            }
            questions.add(question);
        }
        return questions;
    }

}
