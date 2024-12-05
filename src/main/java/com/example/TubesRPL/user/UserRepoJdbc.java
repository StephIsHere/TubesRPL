package com.example.TubesRPL.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoJdbc implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findUser(String email, String password) {
        String sql = "SELECT * FROM \"user\" WHERE email = ? AND password = ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, email, password);
    }

    @Override
    public List<User> findUserByName(String name) {
        String sql = "SELECT * FROM \"user\" WHERE LOWER(nama) LIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, "%" + name.toLowerCase() + "%");
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO \"user\" (nama, email, password, peran, npm) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getNama(),
                user.getEmail(),
                user.getPassword(),
                user.getPeran(),
                user.getNpm());
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM \"user\" ORDER BY nama ASC"; // Ordering by name for better readability
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public List<User> findAllDesc() {
        String sql = "SELECT * FROM \"user\" ORDER BY nama DESC"; // Ordering by name for better readability
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("idUser"),
                resultSet.getString("nama"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("peran"),
                resultSet.getString("npm")
        );
    }
}