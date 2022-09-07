package ru.otus.spring.kilyakov.service.impl;

import ru.otus.spring.kilyakov.domain.Question;
import ru.otus.spring.kilyakov.dao.impl.CsvResourceDaoImpl;
import ru.otus.spring.kilyakov.service.StudentTestingService;

import java.util.List;

public class StudentTestingServiceImpl implements StudentTestingService {

    private final CsvResourceDaoImpl csvResourceDao;

    private List<Question> questions = null;

    public List<Question> getQuestions() {
        return questions;
    }

    public StudentTestingServiceImpl(CsvResourceDaoImpl csvResourceDao) {
        this.csvResourceDao = csvResourceDao;
    }

    @Override
    public void printAllQuestions() {
        try {
            questions = csvResourceDao.readAllQuestions();
        } catch (Exception ex) {
            String cause = "";
            if (ex.getCause() != null) {
                cause = ex.getCause().toString();
            }
            System.out.println("Error has occurred while csvReader.readAllQuestions() method invoke, cause: "
                    + cause);
            questions = null;
        }
        if (questions != null) {
            for (Question question : questions) {
                String builder = question.getQuestion() + " " +
                        question.getAnswer1() + ", " +
                        question.getAnswer2() + ", " +
                        question.getAnswer3() + ", " +
                        question.getAnswer4() + ", " +
                        question.getAnswer5();
                System.out.println(builder);
            }
        }
    }
}
