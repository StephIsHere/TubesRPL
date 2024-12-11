package com.example.TubesRPL.user;

import java.lang.foreign.Linker.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoJdbc implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mencari user berdasarkan email dan passsword
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
    public List<User> findByNpm(String npm){
        String sql = "SELECT * FROM \"user\" WHERE npm LIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, "%" + npm + "%");
    }
    @Override
    public List<User> findById(Long id){
        String sql = "SELECT * FROM \"user\" WHERE idUser LIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, "%" + id + "%");
    }

    @Override
    public List<User> findUserByRole(String role) {
        String sql = "SELECT * FROM \"user\" WHERE peran LIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, role);
    }

    @Override
    public void save(User user) {
        if (user.getIdUser() == null) {
            // Jika idUser null, lakukan INSERT
            String sql = "INSERT INTO \"user\" (nama, email, password, peran, npm, status) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    user.getNama(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPeran(),
                    user.getNpm(),
                    user.getStatus());
        } else {
            // Jika idUser ada, lakukan UPDATE
            String sql = "UPDATE \"user\" SET nama = ?, email = ?, password = ?, peran = ?, npm = ?, status = ? WHERE idUser = ?";
            jdbcTemplate.update(sql,
                    user.getNama(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPeran(),
                    user.getNpm(),
                    user.getStatus(),
                    user.getIdUser());
        }
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO \"user\" (nama, email, password, peran, npm, status) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getNama(),
                user.getEmail(),
                user.getPassword(),
                user.getPeran(),
                user.getNpm(),
                user.getStatus());
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM \"user\" ORDER BY nama ASC"; // Ordering by name for better readability
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public List<User> findAllDesc() {
        String sql = "SELECT * FROM \"user\" ORDER BY nama DESC";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public void setUserInactive(Long idUser) {
        String sql = "UPDATE \"user\" SET status = false WHERE \"idUser\" = ?";
        jdbcTemplate.update(sql, idUser);
    }

    
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("idUser"),
                resultSet.getString("nama"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("peran"),
                resultSet.getString("npm"),
                resultSet.getBoolean("status")
        );
    }
}