package ru.otus.spring.kilyakov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.kilyakov.config.property.StudentTestingProperty;
import ru.otus.spring.kilyakov.dao.impl.CsvResourceDaoImpl;
import ru.otus.spring.kilyakov.service.impl.StudentTestingServiceImpl;

@Configuration
public class StudentTestingServiceConfig {

    @Bean
    public StudentTestingServiceImpl studentTestingService(CsvResourceDaoImpl csvResourceDao,
                                                           StudentTestingProperty studentTestingProperty) {
        return new StudentTestingServiceImpl(csvResourceDao, studentTestingProperty);
    }
}
