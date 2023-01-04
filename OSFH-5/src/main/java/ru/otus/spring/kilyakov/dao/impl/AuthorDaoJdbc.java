package ru.otus.spring.kilyakov.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kilyakov.dao.AuthorDao;
import ru.otus.spring.kilyakov.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insert(Author author) {
        namedParameterJdbcOperations.update("insert into authors (first_name, middle_name, last_name) values " +
                        "(:first_name, :middle_name, :last_name)",
                Map.of("first_name", author.getFirstName(),
                        "middle_name", author.getMiddleName() != null ? author.getMiddleName() : "",
                        "last_name", author.getLastName()
                ));
    }

    @Override
    public void update(Author author) {
        namedParameterJdbcOperations.update("update authors set first_name = :first_name, " +
                        "middle_name = :middle_name, last_name = :last_name where id = :id",
                Map.of("id", author.getId(),
                        "first_name", author.getFirstName(),
                        "middle_name", author.getMiddleName(),
                        "last_name", author.getLastName()));
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, first_name, middle_name, last_name from authors where id = :id", params,
                new AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select id, first_name, middle_name, last_name from authors",
                new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String middleName = resultSet.getString("middle_name");
            String lastName = resultSet.getString("last_name");
            return new Author(id, firstName, middleName, lastName);
        }
    }
}
