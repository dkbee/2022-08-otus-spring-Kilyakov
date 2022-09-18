package ru.otus.spring.kilyakov.service.impl;

import ru.otus.spring.kilyakov.config.property.StudentTestingProperty;
import ru.otus.spring.kilyakov.dao.impl.CsvResourceDaoImpl;
import ru.otus.spring.kilyakov.domain.Question;
import ru.otus.spring.kilyakov.service.StudentTestingService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class StudentTestingServiceImpl implements StudentTestingService {

    private final CsvResourceDaoImpl csvResourceDao;
    private final StudentTestingProperty studentTestingProperty;

    private List<Question> questions = null;

    public StudentTestingServiceImpl(CsvResourceDaoImpl csvResourceDao, StudentTestingProperty studentTestingProperty) {
        this.csvResourceDao = csvResourceDao;
        this.studentTestingProperty = studentTestingProperty;
    }

    @Override
    public List<Question> getQuestions() {
        if (questions == null) {
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
        }
        return questions;
    }

    @Override
    public void executeTest() {
        List<Question> questions = getQuestions();
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        for (Question question :
                questions) {
            outputQuestion(question);
            int choice = getChoiceDigit(scanner);
            int rightAnswer = question.getRightAnswer();
            if (choice == rightAnswer) {
                score++;
            }
        }
        outputRating(score);
    }

    private void outputRating(int score) {
        if (score <= studentTestingProperty.getMarkBad()) {
            System.out.println(MessageFormat.format(studentTestingProperty.getTestFailureMessage(), score));
        }
        if (score == studentTestingProperty.getMarkSatisfactory()) {
            System.out.println(MessageFormat.format(studentTestingProperty.getTestPassedSatisfactoryMessage(),score));
        }
        if (score == studentTestingProperty.getMarkGood()) {
            System.out.println(MessageFormat.format(studentTestingProperty.getTestPassedGoodMessage(), score));
        }
        if (score == studentTestingProperty.getMarkExcellent()) {
            System.out.println(MessageFormat.format(studentTestingProperty.getTestPassedExcellentMessage(), score));
        }
    }

    private int getChoiceDigit(Scanner scanner) {
        while (true) {
            int choice;
            try {
                String innerText = scanner.nextLine();
                choice = Integer.parseInt(innerText);
            } catch (NumberFormatException ex) {
                System.out.println("You are entered not a digit, please try again");
                continue;
            }
            if (choice < 1 || choice > 5) {
                System.out.println("Your answer do  not laying between 1 and 5, please try again");
            } else {
                return choice;
            }
        }
    }

    private void outputQuestion(Question question) {
        System.out.println("Question: ");
        System.out.println(question.getQuestion());
        System.out.println("Answer choices: ");
        String answers = question.getAnswer1() + "\n" +
                question.getAnswer2() + "\n" +
                question.getAnswer3() + "\n" +
                question.getAnswer4() + "\n" +
                question.getAnswer5();
        System.out.println(answers);
        System.out.println("Please choose answer(use digits from 1 to 5): ");
    }

    @Override
    public void printAllQuestions() {
        List<Question> questions = getQuestions();
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
