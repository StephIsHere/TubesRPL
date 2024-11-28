package com.example.TubesRPL.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoJdbc implements UserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findUser (String email, String password) {
        String sql = "SELECT * FROM \"user\" WHERE email = ? AND password = ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, email, password);
    }

    private User mapRowToUser (ResultSet resultSet, int rowNum) throws SQLException {
        return new User (
            resultSet.getInt("idUser"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("nama"),
            resultSet.getString("peran"),
            resultSet.getString("npm")
        );
    }
}
