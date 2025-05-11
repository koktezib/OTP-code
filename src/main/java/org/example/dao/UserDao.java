package org.example.dao;

import org.example.enums.UserRole;
import org.example.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    private final JdbcTemplate jdbc;

    public UserDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<User> userRowMapper = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getInt("id"),
                    rs.getString("login"),
                    rs.getString("password_hash"),
                    UserRole.valueOf(rs.getString("role"))
            );
        }
    };

    public void save(User user) {
        jdbc.update("""
            INSERT INTO users (login, password_hash, role)
            VALUES (?, ?, ?)
            """,
                user.getLogin(),
                user.getPasswordHash(),
                user.getRole().name()
        );
    }

    public Optional<User> findById(int id) {
        return jdbc.query("""
            SELECT id, login, password_hash, role
              FROM users
             WHERE id = ?
        """,
                userRowMapper,
                id
        ).stream().findFirst();
    }

    public Optional<User> findByLogin(String login) {
        return jdbc.query("""
                SELECT id, login, password_hash, role 
                  FROM users 
                 WHERE login = ?
            """,
                userRowMapper,
                login
        ).stream().findFirst();
    }

    public List<User> findAllNonAdmin() {
        return jdbc.query("""
                SELECT id, login, password_hash, role 
                  FROM users 
                 WHERE role <> 'ADMIN'
            """,
                userRowMapper
        );
    }

    public void deleteById(int id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }

    public int countAdmins() {
        return jdbc.queryForObject(
                "SELECT count(*) FROM users WHERE role = 'ADMIN'",
                Integer.class
        );
    }
}