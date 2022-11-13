package ru.otus.spring.kilyakov.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    public int count() {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from authors",
                sqlParameterSource, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Author author) {
        namedParameterJdbcOperations.update("insert into authors (id, first_name, middle_name, last_name) values " +
                        "(:id, :first_name, :middle_name, :last_name)",
                Map.of("id", author.getId(), "name", author.getFirstName()));
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

    @Override
    public Author findAuthor(Author author) {
        try {
            return namedParameterJdbcOperations.queryForObject(
                    " select id, " +
                            " first_name," +
                            " middle_name, " +
                            " last_name from authors " +
                            " where lower(first_name) = lower(:first_name) " +
                            " and lower(middle_name) = lower(:middle_name) " +
                            " and lower(last_name) = lower(:last_name)",
                    Map.of("first_name", author.getFirstName(), "middle_name", author.getMiddleName(),
                            "last_name", author.getLastName()), new AuthorMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
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
