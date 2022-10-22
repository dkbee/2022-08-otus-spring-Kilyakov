package ru.otus.spring.kilyakov.config;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.kilyakov.config.property.LocalizationProperty;
import ru.otus.spring.kilyakov.domain.Question;

@Configuration
public class CsvResourceDaoConfig {

    private final LocalizationProperty localizationProperty;

    public CsvResourceDaoConfig(LocalizationProperty localizationProperty) {
        this.localizationProperty = localizationProperty;
    }

    @Bean
    public FlatFileItemReader<Question> flatFileItemReader() {
        FlatFileItemReader<Question> flatFileItemReader = new FlatFileItemReader<>();
        String path = localizationProperty.getDefaultPath();
        if (localizationProperty.getLocalePaths() != null && localizationProperty.getLocale() != null) {
            path = localizationProperty.getLocalePaths().get(localizationProperty.getLocale().toString());
        }
        flatFileItemReader.setResource(new ClassPathResource(path));
        DefaultLineMapper<Question> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
            setTargetType(Question.class);
        }});
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer() {{
            setNames("question", "answer1", "answer2", "answer3", "answer4", "answer5", "right_answer");
        }});
        flatFileItemReader.setLineMapper(defaultLineMapper);
        return flatFileItemReader;
    }


}
