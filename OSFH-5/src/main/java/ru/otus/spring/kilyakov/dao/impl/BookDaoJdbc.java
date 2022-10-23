package ru.otus.spring.kilyakov.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kilyakov.dao.BookDao;
import ru.otus.spring.kilyakov.domain.Author;
import ru.otus.spring.kilyakov.domain.Book;
import ru.otus.spring.kilyakov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from books",
                sqlParameterSource, Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Book book) {
        namedParameterJdbcOperations.update("insert into books (id, name, author_id, genre_id) values (:id, :name, :author_id, :genre_id)",
                Map.of("id", book.getId(), "name", book.getName(), "author_id", book.getAuthor().getId(), "genre_id", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select b.id as id, " +
                "b.name as name, " +
                "a.id as authorId," +
                "a.first_name as firstName, " +
                "a.middle_name as middleName, " +
                "a.last_name as lastName, " +
                "g.id as genreId, " +
                "g.name as genreName " +
                "from books b " +
                "left join authors a on b.author_id = a.id " +
                "left join genres g on b.genre_id = g.id " +
                "where b.id = :id", params, new PersonMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select b.id as id, " +
                "b.name as name, " +
                "a.id as authorId," +
                "a.first_name as firstName, " +
                "a.middle_name as middleName, " +
                "a.last_name as lastName, " +
                "g.id as genreId, " +
                "g.name as genreName " +
                "from books b " +
                "left join authors a on b.author_id = a.id " +
                "left join genres g on b.genre_id = g.id", new PersonMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    private static class PersonMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Long authorId = resultSet.getLong("authorId");
            String authorFirsName = resultSet.getString("firstName");
            String authorMiddleName = resultSet.getString("middleName");
            String authorLastName = resultSet.getString("lastName");
            Long genreId = resultSet.getLong("genreId");
            String genreName = resultSet.getString("genreName");
            return new Book(id, name, new Author(authorId, authorFirsName, authorMiddleName, authorLastName),
                    new Genre(genreId, genreName));
        }
    }
}
