package com.example.TubesRPL.sidang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.example.TubesRPL.komponenNilai.*;

@Repository
public class SidangRepoJdbc implements SidangRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private KomponenNilaiRepository kompNilaiRepo;

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
    public void addSidangDosen (int idSidang, Long nikPembimbing1, Long nikPembimbing2, Long nikPenguji1, Long nikPenguji2){
        String sql = "INSERT INTO sidangDosen (idSidang, idUser, peran) VALUES (?, ?, ?)";
        
        jdbcTemplate.update(sql, idSidang, nikPembimbing1, "Pembimbing 1");
        jdbcTemplate.update(sql, idSidang, nikPembimbing2, "Pembimbing 2");
        jdbcTemplate.update(sql, idSidang, nikPenguji1, "Penguji 1");
        jdbcTemplate.update(sql, idSidang, nikPenguji2, "Penguji 2");
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
    public List<Sidang> findAllSidangWithIdUser(long iduser) {
        String sql = "SELECT s.idSidang, s.jenisTA, m.nama AS penulis, s.topik, s.judul, s.tempat, " +
                    "s.tanggal, s.waktu, s.catatan, s.status, s.ttdKetuaPenguji, s.ttdTimPenguji, " +
                    "s.ttdPembimbing1, s.ttdPembimbing2, s.ttdMahasiswa, s.ttdKoordinator, " +
                    "s.idKoordinator, s.idMahasiswa " +
                    "FROM sidangdosen sd " +
                    "JOIN users ON sd.iduser = users.iduser " +
                    "JOIN sidang s ON sd.idsidang = s.idsidang " +
                    "JOIN users m ON m.iduser = s.idmahasiswa " +
                    "WHERE users.iduser = ?";
        return jdbcTemplate.query(sql, sidangRowMapper, iduser);
    }


    @Override
    public boolean addCatatanSidang(Sidang sidang, String catatan) { 
        String sql = "UPDATE sidang SET catatan = ? WHERE judul = ?"; 
        int rowsAffected = jdbcTemplate.update(sql, catatan, sidang.getJudul()); 
        return rowsAffected > 0; 
    } 

    @Override
    public void saveNilaiPenguji1(int idSidang, int ttl, int km, int pt, int p, int pm) {
        String sql = "INSERT INTO komponenNilaiSidang (idSidang, idKomponen, nilai) "+    "VALUES (?, 5, ?), "+
        "(?, 6, ?), "+
        "(?, 7, ?), "+
        "(?, 8, ?), "+
        "(?, 9, ?); ";
        jdbcTemplate.update(sql, idSidang, ttl, idSidang, km, idSidang, pt, idSidang, p, idSidang, pm);
    }

    @Override
    public void saveNilaiPenguji2(int idSidang, int ttl, int km, int pt, int p, int pm) {
        String sql = "INSERT INTO komponenNilaiSidang (idSidang, idKomponen, nilai) "+    "VALUES (?, 14, ?), "+
        "(?, 15, ?), "+
        "(?, 16, ?), "+
        "(?, 17, ?), "+
        "(?, 18, ?); ";
        jdbcTemplate.update(sql, idSidang, ttl, idSidang, km, idSidang, pt, idSidang, p, idSidang, pm);
    }

    @Override
    public void saveNilaiPembimbing(int idSidang, int ttl, int km, int pb, int pm) {
        String sql = "INSERT INTO komponenNilaiSidang (idSidang, idKomponen, nilai) "+    "VALUES (?, 10, ?), "+
        "(?, 11, ?), "+
        "(?, 12, ?), "+
        "(?, 13, ?); ";
        jdbcTemplate.update(sql, idSidang, ttl, idSidang, km, idSidang, pb, idSidang, pm);
    }

    @Override
    public boolean checkNilaiPenguji1(int idSidang) {
        String sql = "SELECT COUNT(*) FROM komponenNilaiSidang WHERE idKomponen = 5 AND idSidang = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{idSidang}, Integer.class);
        return count > 0;
    }

    @Override
    public boolean checkNilaiPenguji2(int idSidang) {
        String sql = "SELECT COUNT(*) FROM komponenNilaiSidang WHERE idKomponen = 14 AND idSidang = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{idSidang}, Integer.class);
        return count > 0;
    }

    @Override
    public boolean checkNilaiPembimbing(int idSidang) {
        String sql = "SELECT COUNT(*) FROM komponenNilaiSidang WHERE idKomponen = 10 AND idSidang = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{idSidang}, Integer.class);
        return count > 0;
    }

    @Override
    public void saveNilaiMain(int idSidang) {
        boolean nPeng1 = checkNilaiPenguji1(idSidang);
        boolean nPeng2 = checkNilaiPenguji2(idSidang);
        boolean nPem = checkNilaiPembimbing(idSidang);
        if (nPeng1 && nPeng2 && nPem) {
            String sql = "INSERT INTO komponenNilaiSidang (idSidang, idKomponen, nilai) "+  "VALUES (?, 1, ?), "+
            "(?, 2, ?), "+
            "(?, 3, ?), "+
            "(?, 4, ?), "+
            "(?, 19, ?); ";

            double nilaiPeng1 = getNilaiPerKomponen(idSidang, 5)*kompNilaiRepo.getBobot(5) + getNilaiPerKomponen(idSidang, 6)*kompNilaiRepo.getBobot(6) + getNilaiPerKomponen(idSidang, 7)*kompNilaiRepo.getBobot(7) + getNilaiPerKomponen(idSidang, 8)*kompNilaiRepo.getBobot(8) + getNilaiPerKomponen(idSidang, 9)*kompNilaiRepo.getBobot(9);

            double nilaiPeng2 = getNilaiPerKomponen(idSidang, 14)*kompNilaiRepo.getBobot(14) + getNilaiPerKomponen(idSidang, 15)*kompNilaiRepo.getBobot(15) + getNilaiPerKomponen(idSidang, 16)*kompNilaiRepo.getBobot(16) + getNilaiPerKomponen(idSidang, 17)*kompNilaiRepo.getBobot(17) + getNilaiPerKomponen(idSidang, 18)*kompNilaiRepo.getBobot(18);

            double nilaiPem = getNilaiPerKomponen(idSidang, 10)*kompNilaiRepo.getBobot(10) + getNilaiPerKomponen(idSidang, 11)*kompNilaiRepo.getBobot(11) + getNilaiPerKomponen(idSidang, 12)*kompNilaiRepo.getBobot(12) + getNilaiPerKomponen(idSidang, 13)*kompNilaiRepo.getBobot(13);

            double nilaiKoord = 100.0;

            double nilaiAkhir = nilaiPeng1*kompNilaiRepo.getBobot(1) + nilaiPeng2*kompNilaiRepo.getBobot(2) + nilaiPem*kompNilaiRepo.getBobot(3) + nilaiKoord*kompNilaiRepo.getBobot(4);

            jdbcTemplate.update(sql, idSidang, nilaiPeng1, idSidang, nilaiPeng2, idSidang, nilaiPem, idSidang, nilaiKoord, idSidang, nilaiAkhir);
        }
    }

    public int getNilaiPerKomponen (int idSidang, int idKomponen) {
        String sql = "SELECT nilai FROM komponenNilaiSidang WHERE idKomponen = ? AND idSidang = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idKomponen, idSidang}, Integer.class);
    }

    public void updateStatusSidang (int idSidang, String status) {
        String sql = "UPDATE sidang SET status = ? WHERE idSidang = ?";
        jdbcTemplate.update(sql, status, idSidang);
    }

}