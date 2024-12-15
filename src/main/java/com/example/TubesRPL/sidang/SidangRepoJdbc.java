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
        // Membuat SQL query yang tidak sensitif terhadap case
        String sql = "SELECT s.*, u.nama AS penulis " +
                     "FROM sidang s " +
                     "JOIN users u ON s.idMahasiswa = u.idUser " +
                     "WHERE LOWER(s.judul) LIKE LOWER(?)";
        // Menggunakan jdbcTemplate untuk menjalankan query, dengan wildcard untuk pencarian sebagian kata
        return jdbcTemplate.query(sql, sidangRowMapper, "%" + judul.trim() + "%");
    }
    
    
    @Override
    public List<Sidang> findAllSidangWithPenulis() {
        String sql = "SELECT s.*, u.nama AS penulis " +
                     "FROM sidang s " +
                     "JOIN users u ON s.idMahasiswa = u.idUser";
        return jdbcTemplate.query(sql, sidangRowMapper);
    }

    @Override
    public Sidang addPengujiandPembimbing(String judul) {
        // Query untuk setiap peran
        String searchPenguji1 = "select users.nama from sidangdosen \n" +
                "join users on sidangdosen.iduser = users.iduser\n" +
                "join sidang on sidang.idsidang = sidangdosen.idsidang \n" +
                "where sidang.judul LIKE ? AND sidangdosen.peran LIKE 'Penguji 1'";
        String searchPenguji2 = "select users.nama from sidangdosen \n" +
                "join users on sidangdosen.iduser = users.iduser\n" +
                "join sidang on sidang.idsidang = sidangdosen.idsidang \n" +
                "where sidang.judul LIKE ? AND sidangdosen.peran LIKE 'Penguji 2'";
        String searchPembimbing1 = "select users.nama from sidangdosen \n" +
                "join users on sidangdosen.iduser = users.iduser\n" +
                "join sidang on sidang.idsidang = sidangdosen.idsidang \n" +
                "where sidang.judul LIKE ? AND sidangdosen.peran LIKE 'Pembimbing 1'";
        String searchPembimbing2 = "select users.nama from sidangdosen \n" +
                "join users on sidangdosen.iduser = users.iduser\n" +
                "join sidang on sidang.idsidang = sidangdosen.idsidang \n" +
                "where sidang.judul LIKE ? AND sidangdosen.peran LIKE 'Pembimbing 2'";
    
        // Ambil hasil query untuk setiap peran
        String pembimbing1 = jdbcTemplate.query(searchPembimbing1, 
            new Object[]{judul}, 
            (rs, rowNum) -> rs.getString("nama")
        ).stream().findFirst().orElse(null);
    
        String pembimbing2 = jdbcTemplate.query(searchPembimbing2, 
            new Object[]{judul}, 
            (rs, rowNum) -> rs.getString("nama")
        ).stream().findFirst().orElse(null);
    
        String penguji1 = jdbcTemplate.query(searchPenguji1, 
            new Object[]{judul}, 
            (rs, rowNum) -> rs.getString("nama")
        ).stream().findFirst().orElse(null);
    
        String penguji2 = jdbcTemplate.query(searchPenguji2, 
            new Object[]{judul}, 
            (rs, rowNum) -> rs.getString("nama")
        ).stream().findFirst().orElse(null);

    
        // Update objek Sidang
        Sidang sidang = findSidangByJudul(judul).get(0);
        sidang.setNamaPembimbing1(pembimbing1);
        sidang.setNamaPembimbing2(pembimbing2);
        sidang.setNamaPenguji1(penguji1);
        sidang.setNamaPenguji2(penguji2);
        sidang.setNamaKetuaPenguji(penguji1);
        return sidang;
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
    public void addSidangDosen (Sidang sidang, String nikPembimbing1, String nikPembimbing2, String nikPenguji1, String nikPenguji2){
        String sql2 = "INSERT INTO sidangDosen (idSidang, idUser, peran) VALUES (?, ?, ?)";
    
        // Contoh data yang diambil dari sidang (disesuaikan kebutuhan aplikasi)
        jdbcTemplate.update(sql2,
            sidang.getIdSidang(), 
            nikPembimbing1,
            "Pembimbing 1"     
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
            sidang.setJenisTA(rs.getString("jenisTA"));
            sidang.setNamaPenulis(rs.getString("penulis"));
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

    @Override
    public List<Sidang> findAllSidangByID(Long idMahasiswa) {
        String sql = "SELECT s.*, u.nama AS penulis " +
                     "FROM sidang s " +
                     "JOIN users u ON s.idMahasiswa = u.idUser " +
                     "WHERE s.idMahasiswa = ?";
        return jdbcTemplate.query(sql, sidangRowMapper, idMahasiswa);
    }

    @Override
    public boolean addCatatanSidang(Sidang sidang, String catatan) { 
        String sql = "UPDATE sidang SET catatan = ? WHERE judul = ?"; 
        int rowsAffected = jdbcTemplate.update(sql, catatan, sidang.getJudul()); 
        return rowsAffected > 0; 
    }  
}