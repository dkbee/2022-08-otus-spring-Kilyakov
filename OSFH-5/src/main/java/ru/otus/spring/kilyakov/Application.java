package ru.otus.spring.kilyakov;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.kilyakov.dao.BookDao;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;

import java.sql.SQLException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        BookDao dao = context.getBean(BookDao.class);

        System.out.println("All count " + dao.count());

        dao.insert(new Book(3L, "Bravest warriors", new Author(1L, "Petr", "Petrovich", "Petrov"), new Genre(1L, "Fantasy")));

        System.out.println("All count " + dao.count());

        Book book = dao.getById(2);

        System.out.println("Book id: " + book.getId() + " name: " + book.getName());

        System.out.println(dao.getAll());

        Console.main(args);
    }
}
