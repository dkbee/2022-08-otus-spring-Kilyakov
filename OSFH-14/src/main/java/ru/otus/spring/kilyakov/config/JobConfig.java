package ru.otus.spring.kilyakov.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import ru.otus.spring.kilyakov.domain.relational.Author;
import ru.otus.spring.kilyakov.domain.relational.Book;
import ru.otus.spring.kilyakov.domain.relational.Comment;
import ru.otus.spring.kilyakov.domain.relational.Genre;
import ru.otus.spring.kilyakov.repository.AuthorRepository;
import ru.otus.spring.kilyakov.repository.BookRepository;
import ru.otus.spring.kilyakov.repository.GenreRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Configuration
@EnableBatchProcessing
public class JobConfig {
    private static final int CHUNK_SIZE = 100;
    private final Logger logger = LoggerFactory.getLogger("Batch");
    public static final String MIGRATE_LIBRARY_JOB_NAME = "migrateLibraryJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public RepositoryItemReader<Author> authorReader(AuthorRepository repository) {
        var repositoryReader = new RepositoryItemReader<Author>();
        repositoryReader.setRepository(repository);
        repositoryReader.setMethodName("findAll");
        repositoryReader.setSort(new HashMap<String, Sort.Direction>());
        return repositoryReader;
    }

    @Bean
    public RepositoryItemReader<Genre> genreReader(GenreRepository repository) {
        var repositoryReader = new RepositoryItemReader<Genre>();
        repositoryReader.setRepository(repository);
        repositoryReader.setMethodName("findAll");
        repositoryReader.setSort(new HashMap<String, Sort.Direction>());
        return repositoryReader;
    }

    @Bean
    public RepositoryItemReader<Book> bookReader(BookRepository repository) {
        var repositoryReader = new RepositoryItemReader<Book>();
        repositoryReader.setRepository(repository);
        repositoryReader.setMethodName("findAll");
        repositoryReader.setSort(new HashMap<String, Sort.Direction>());
        return repositoryReader;
    }

    @Bean
    public ItemProcessor<Author, ru.otus.spring.kilyakov.domain.nosql.Author> authorItemProcessor() {
        return item -> ru.otus.spring.kilyakov.domain.nosql.Author.builder()
                .id(item.getId().toString())
                .firstName(item.getFirstName())
                .middleName(item.getMiddleName())
                .lastName(item.getLastName())
                .build();
    }

    @Bean
    public ItemProcessor<Genre, ru.otus.spring.kilyakov.domain.nosql.Genre> genreItemProcessor() {
        return item -> ru.otus.spring.kilyakov.domain.nosql.Genre.builder()
                .id(item.getId().toString())
                .name(item.getName())
                .build();
    }

    @Bean
    public ItemProcessor<Book, ru.otus.spring.kilyakov.domain.nosql.Book> bookItemProcessor() {
        return item -> {
            var comments = new ArrayList<ru.otus.spring.kilyakov.domain.nosql.Comment>();
            for (Comment comment :
                    item.getComments()) {
                comments.add(ru.otus.spring.kilyakov.domain.nosql.Comment.builder()
                        .comment(comment.getComment())
                        .build());
            }
            return ru.otus.spring.kilyakov.domain.nosql.Book.builder()
                    .id(item.getId().toString())
                    .name(item.getName())
                    .genre(ru.otus.spring.kilyakov.domain.nosql.Genre.builder()
                            .id(item.getGenre().getId().toString())
                            .name(item.getGenre().getName())
                            .build())
                    .author(ru.otus.spring.kilyakov.domain.nosql.Author.builder()
                            .id(item.getAuthor().getId().toString())
                            .firstName(item.getAuthor().getFirstName())
                            .middleName(item.getAuthor().getMiddleName())
                            .lastName(item.getAuthor().getLastName())
                            .build())
                    .comments(comments)
                    .build();
        };
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.kilyakov.domain.nosql.Author> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.kilyakov.domain.nosql.Author>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.kilyakov.domain.nosql.Genre> genreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.kilyakov.domain.nosql.Genre>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.kilyakov.domain.nosql.Book> bookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.kilyakov.domain.nosql.Book>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public Job migrateLibraryJob(Step transformAuthorsStep, Step transformGenresStep, Step transformBooksStep) {
        return jobBuilderFactory.get(MIGRATE_LIBRARY_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(transformGenresStep)
                .next(transformBooksStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step transformAuthorsStep(RepositoryItemReader<Author> authorReader,
                                     ItemProcessor<Author, ru.otus.spring.kilyakov.domain.nosql.Author> authorItemProcessor,
                                     MongoItemWriter<ru.otus.spring.kilyakov.domain.nosql.Author> authorWriter
    ) {
        return stepBuilderFactory.get("authorsStep")
                .<Author, ru.otus.spring.kilyakov.domain.nosql.Author>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .processor(authorItemProcessor)
                .writer(authorWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Author o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Author o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(@NonNull Author o, ru.otus.spring.kilyakov.domain.nosql.Author o2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(@NonNull Author o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }

    @Bean
    public Step transformGenresStep(RepositoryItemReader<Genre> genreReader,
                                    ItemProcessor<Genre, ru.otus.spring.kilyakov.domain.nosql.Genre> genreItemProcessor,
                                    MongoItemWriter<ru.otus.spring.kilyakov.domain.nosql.Genre> genreWriter
    ) {
        return stepBuilderFactory.get("genresStep")
                .<Genre, ru.otus.spring.kilyakov.domain.nosql.Genre>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .processor(genreItemProcessor)
                .writer(genreWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Genre o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Genre o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(@NonNull Genre o, ru.otus.spring.kilyakov.domain.nosql.Genre o2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(@NonNull Genre o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }

    @Bean
    public Step transformBooksStep(RepositoryItemReader<Book> bookReader,
                                   ItemProcessor<Book, ru.otus.spring.kilyakov.domain.nosql.Book> bookItemProcessor,
                                   MongoItemWriter<ru.otus.spring.kilyakov.domain.nosql.Book> bookWriter
    ) {
        return stepBuilderFactory.get("booksStep")
                .<Book, ru.otus.spring.kilyakov.domain.nosql.Book>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookItemProcessor)
                .writer(bookWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Book o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Book o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(@NonNull Book o, ru.otus.spring.kilyakov.domain.nosql.Book o2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(@NonNull Book o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }

}
