package ru.otus.spring.kilyakov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Application.class, args);
        System.out.printf("%n%s%n", "http://localhost:8080/book/");
        log.info("app started");
    }
}
