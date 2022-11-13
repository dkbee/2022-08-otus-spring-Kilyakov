package ru.otus.spring.kilyakov.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kilyakov.dao.GenreDao;
import ru.otus.spring.kilyakov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from genres",
                sqlParameterSource, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Genre genre) {
        namedParameterJdbcOperations.update("insert into genres (id, name) values (:id, :name)",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from genres where id = :id", params, new GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select id, name from genres", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from genres where id = :id", params
        );
    }

    @Override
    public Genre findGenre(Genre genre) {
        try {
            return namedParameterJdbcOperations.queryForObject(
                    " select id, " +
                            " name " +
                            " from genres " +
                            " where lower(name) = lower(:name) ",
                    Map.of("name", genre.getName()), new GenreDaoJdbc.GenreMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
