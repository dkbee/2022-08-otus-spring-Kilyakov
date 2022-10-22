package ru.otus.spring.kilyakov.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.kilyakov.pojo.Mark;

@Getter
@Setter
@ConfigurationProperties(prefix = "test")
public class StudentTestingProperty {

    private Integer passingThreshold;

    private String excellentMessage;

    private String goodMessage;

    private String satisfactoryMessage;

    private String failureMessage;

    private Mark mark;
}
