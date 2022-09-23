package ru.otus.spring.kilyakov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.kilyakov.config.property.StudentTestingProperty;
import ru.otus.spring.kilyakov.dao.CsvResourceDao;
import ru.otus.spring.kilyakov.domain.Question;
import ru.otus.spring.kilyakov.service.IoService;
import ru.otus.spring.kilyakov.service.StudentTestingService;

import java.text.MessageFormat;
import java.util.List;

@Service
@Slf4j
public class StudentTestingServiceImpl implements StudentTestingService {

    private static final int MIN_OPTION_ANSWER = 1;
    private static final int MAX_OPTION_ANSWER = 5;

    private final CsvResourceDao csvResourceDao;
    private final StudentTestingProperty studentTestingProperty;

    private final IoService ioService;

    private List<Question> questions = null;

    public StudentTestingServiceImpl(CsvResourceDao csvResourceDao, StudentTestingProperty studentTestingProperty,
                                     IoService ioService) {
        this.csvResourceDao = csvResourceDao;
        this.studentTestingProperty = studentTestingProperty;
        this.ioService = ioService;
    }

    @Override
    public void executeTest() {
        List<Question> questions = getQuestions();
        int score = 0;
        if (questions != null && questions.size() > 0) {
            for (Question question :
                    questions) {
                outputQuestion(question);
                int choice = ioService.readDigit(MIN_OPTION_ANSWER, MAX_OPTION_ANSWER);
                int rightAnswer = question.getRightAnswer();
                if (choice == rightAnswer) {
                    score++;
                }
            }
            outputRating(score);
        }
    }

    private List<Question> getQuestions() {
        if (questions == null) {
            try {
                questions = csvResourceDao.readAllQuestions();
            } catch (Exception ex) {
                log.warn("Error has occurred while csvReader.readAllQuestions() method invoke, cause: "
                        + ex);
                questions = null;
            }
        }
        return questions;
    }

    private void outputRating(int score) {
        if (score <= studentTestingProperty.getMarkBad()) {
            ioService.writeString(MessageFormat.format(studentTestingProperty.getTestFailureMessage(), score));
        }
        if (score == studentTestingProperty.getMarkSatisfactory()) {
            ioService.writeString(MessageFormat.format(studentTestingProperty.getTestPassedSatisfactoryMessage(),score));
        }
        if (score == studentTestingProperty.getMarkGood()) {
            ioService.writeString(MessageFormat.format(studentTestingProperty.getTestPassedGoodMessage(), score));
        }
        if (score == studentTestingProperty.getMarkExcellent()) {
            ioService.writeString(MessageFormat.format(studentTestingProperty.getTestPassedExcellentMessage(), score));
        }
    }

    private void outputQuestion(Question question) {
        ioService.writeString("Question: ");
        ioService.writeString(question.getQuestion());
        ioService.writeString("Answer choices: ");
        String answers = question.getAnswer1() + "\n" +
                question.getAnswer2() + "\n" +
                question.getAnswer3() + "\n" +
                question.getAnswer4() + "\n" +
                question.getAnswer5();
        ioService.writeString(answers);
        ioService.writeString("Please choose answer(use digits from 1 to 5): ");
    }
}
