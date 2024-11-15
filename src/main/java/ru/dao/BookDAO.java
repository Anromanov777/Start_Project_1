package ru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.model.Book;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY 1", new BeanPropertyRowMapper<>(Book.class));
    }

    public void saveBook(String name, String author) {
        jdbcTemplate.update("INSERT INTO book (name, author) VALUES (?,?)", name, author);
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET name=?,author=? WHERE id=?", book.getName(), book.getAuthor(), id);
    }

    public void free(int id) {
        jdbcTemplate.update("UPDATE book SET id_person=null WHERE id=?", id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void setPerson(int idBook, int idPerson) {
        jdbcTemplate.update("UPDATE book SET id_person=? WHERE id=?", idPerson, idBook);
    }

}
