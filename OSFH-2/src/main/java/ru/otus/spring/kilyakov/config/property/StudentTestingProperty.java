package ru.otus.spring.kilyakov.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:student-testing.properties")
@Getter
@Setter
public class StudentTestingProperty {

    @Value("${mark.bad}")
    private Integer markBad;

    @Value("${mark.satisfactory}")
    private Integer markSatisfactory;

    @Value("${mark.good}")
    private Integer markGood;

    @Value("${mark.excellent}")
    private Integer markExcellent;

    @Value("${test.passing.threshold}")
    private String testPassingThreshold;

    @Value("${test.passed.excellent.message}")
    private String testPassedExcellentMessage;

    @Value("${test.passed.good.message}")
    private String testPassedGoodMessage;

    @Value("${test.passed.satisfactory.message}")
    private String testPassedSatisfactoryMessage;

    @Value("${test.failure.message}")
    private String testFailureMessage;
}
