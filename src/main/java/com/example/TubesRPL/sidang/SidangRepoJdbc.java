package com.example.TubesRPL.sidang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class SidangRepoJdbc implements SidangRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Sidang> findSidang(int idSidang) {
        String sql = "SELECT s.*, u.nama AS penulis " +
                     "FROM sidang s " +
                     "JOIN users u ON s.idMahasiswa = u.idUser " +
                     "WHERE s.idSidang = ?";
        return jdbcTemplate.query(sql, sidangRowMapper, idSidang);
    }
    
    @Override
    public List<Sidang> findSidangByJudul(String judul) {
        String sql = "SELECT s.*, u.nama AS penulis " +
                     "FROM sidang s " +
                     "JOIN users u ON s.idMahasiswa = u.idUser " +
                     "WHERE LOWER(s.judul) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, sidangRowMapper, "%" + judul + "%");
    }    

    @Override
    public List<Sidang> findAllSidangWithPenulis() {
        String sql = "SELECT s.*, u.nama AS penulis " +
                     "FROM sidang s " +
                     "JOIN users u ON s.idMahasiswa = u.idUser";
        return jdbcTemplate.query(sql, sidangRowMapper);
    }

    @Override
    public void addSidang(Sidang sidang) {
        String sql = "INSERT INTO sidang (jenisTA, topik, judul, tempat, tanggal, waktu, status, idKoordinator, idMahasiswa) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
        sidang.getJenisTA(),
        sidang.getTopik(),
        sidang.getJudul(),
        sidang.getTempat(),
        sidang.getTanggal(),
        sidang.getWaktu(),
        sidang.getStatus(),
        sidang.getIdKoordinator(),
        sidang.getIdMahasiswa()
        );
    }

    @Override
    public List<Sidang> findAll() {
        String sql = "SELECT * FROM sidang";
        return jdbcTemplate.query(sql, sidangRowMapper);
    }

    private final RowMapper<Sidang> sidangRowMapper = new RowMapper<Sidang>() {
        @Override
        public Sidang mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sidang sidang = new Sidang();
            sidang.setIdSidang(rs.getInt("idSidang"));
            sidang.setNamaPenulis(rs.getString("penulis"));
            sidang.setJenisTA(rs.getString("jenisTA"));
            sidang.setTopik(rs.getString("topik"));
            sidang.setJudul(rs.getString("judul"));
            sidang.setTempat(rs.getString("tempat"));
            sidang.setTanggal(rs.getDate("tanggal").toLocalDate());
            sidang.setWaktu(rs.getTime("waktu").toLocalTime());
            sidang.setCatatan(rs.getString("catatan"));
            sidang.setStatus(rs.getString("status"));
            sidang.setTtdKetuaPenguji(rs.getBytes("ttdKetuaPenguji"));
            sidang.setTtdTimPenguji(rs.getBytes("ttdTimPenguji"));
            sidang.setTtdPembimbing1(rs.getBytes("ttdPembimbing1"));
            sidang.setTtdPembimbing2(rs.getBytes("ttdPembimbing2"));
            sidang.setTtdMahasiswa(rs.getBytes("ttdMahasiswa"));
            sidang.setTtdKoordinator(rs.getBytes("ttdKoordinator"));
            sidang.setIdKoordinator(rs.getLong("idKoordinator"));
            sidang.setIdMahasiswa(rs.getLong("idMahasiswa"));
            return sidang;
        }
    };
}