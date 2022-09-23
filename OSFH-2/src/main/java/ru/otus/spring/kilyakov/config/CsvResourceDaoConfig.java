package ru.otus.spring.kilyakov.config;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.kilyakov.domain.Question;

@Configuration
public class CsvResourceDaoConfig {

    @Bean
    public FlatFileItemReader<Question> flatFileItemReader() {
        FlatFileItemReader<Question> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("test-cases.csv"));
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
