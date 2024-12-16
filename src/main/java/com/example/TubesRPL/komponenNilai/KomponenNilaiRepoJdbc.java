package com.example.TubesRPL.komponenNilai;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KomponenNilaiRepoJdbc implements KomponenNilaiRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveKomponen (
        double bobotNilaiKetuaTimPenguji,
        double bobotNilaiAnggotaTimPenguji,
        double bobotNilaiPembimbing,
        double bobotNilaiKoordinator,
        double tataTulisLaporanPenguji,
        double kelengkapanMateriPenguji,
        double pencapaianTujuanPenguji,
        double penguasaanMateriPenguji,
        double presentasiPenguji,
        double tataTulisLaporanPembimbing,
        double kelengkapanMateriPembimbing,
        double prosesBimbingan,
        double penguasaanMateriPembimbing) throws SQLException {
            String sql = "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Ketua Tim Penguji', ?, 'bobot nilai'); " +
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Anggota Tim Penguji', ?, 'bobot nilai'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Pembimbing', ?, 'bobot nilai'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Koordinator', ?, 'bobot nilai'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Tata Tulis Laporan', ?, 'penguji'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Kelengkapan Materi', ?, 'penguji'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Pencapaian Tujuan', ?, 'penguji'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Penguasaan Materi', ?, 'penguji'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Presentasi', ?, 'penguji'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Tata Tulis Laporan', ?, 'pembimbing'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Kelengkapan Materi', ?, 'pembimbing'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Proses Bimbingan', ?, 'pembimbing'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Penguasaan Materi', ?, 'pembimbing'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Tata Tulis Laporan', ?, 'penguji2'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Kelengkapan Materi', ?, 'penguji2'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Pencapaian Tujuan', ?, 'penguji2'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Penguasaan Materi', ?, 'penguji2'); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe) VALUES ('Presentasi', ?, 'penguji2'); ";
            jdbcTemplate.update(
                sql, bobotNilaiKetuaTimPenguji, 
                bobotNilaiAnggotaTimPenguji, 
                bobotNilaiPembimbing, 
                bobotNilaiKoordinator,
                tataTulisLaporanPenguji,
                kelengkapanMateriPenguji,
                pencapaianTujuanPenguji,
                penguasaanMateriPenguji,
                presentasiPenguji,
                tataTulisLaporanPembimbing,
                kelengkapanMateriPembimbing,
                prosesBimbingan,
                penguasaanMateriPembimbing,
                tataTulisLaporanPenguji,
                kelengkapanMateriPenguji,
                pencapaianTujuanPenguji,
                penguasaanMateriPenguji,
                presentasiPenguji);
    }

    @Override
    public boolean checkKomponen() {
        String sql = "SELECT COUNT(*) FROM komponenNilai";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public List<KomponenNilai> getAll() {
        String sql = "SELECT idKomponen, namaKomponen, bobotKomponen FROM komponenNilai LIMIT 13";
        return jdbcTemplate.query(sql, this::mapRowToKomponenNilai);
    }
    private KomponenNilai mapRowToKomponenNilai (ResultSet resultSet, int rowNum) throws SQLException{
        return new KomponenNilai(
            resultSet.getInt("idKomponen"),
            resultSet.getString("namaKomponen"),
            resultSet.getDouble("bobotKomponen")
        );

    }

    @Override
    public double getBobot(int idKomponen) {
        String sql = "SELECT bobotKomponen FROM komponenNilai WHERE idKomponen = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idKomponen}, Double.class);
    }

    @Override
    public void editKomponen (
        double bobotNilaiKetuaTimPenguji,
        double bobotNilaiAnggotaTimPenguji,
        double bobotNilaiPembimbing,
        double bobotNilaiKoordinator,
        double tataTulisLaporanPenguji,
        double kelengkapanMateriPenguji,
        double pencapaianTujuanPenguji,
        double penguasaanMateriPenguji,
        double presentasiPenguji,
        double tataTulisLaporanPembimbing,
        double kelengkapanMateriPembimbing,
        double prosesBimbingan,
        double penguasaanMateriPembimbing) throws SQLException {

        String sql = "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 1; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 2; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 3; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 4; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 5; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 6; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 7; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 8; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 9; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 10; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 11; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 12; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 13;" +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 14; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 15; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 16; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 17; " +
                    "UPDATE komponenNilai SET bobotKomponen = ? WHERE idKomponen = 18;";

        jdbcTemplate.update(
            sql, 
            bobotNilaiKetuaTimPenguji, 
            bobotNilaiAnggotaTimPenguji, 
            bobotNilaiPembimbing, 
            bobotNilaiKoordinator, 
            tataTulisLaporanPenguji, 
            kelengkapanMateriPenguji, 
            pencapaianTujuanPenguji, 
            penguasaanMateriPenguji, 
            presentasiPenguji, 
            tataTulisLaporanPembimbing, 
            kelengkapanMateriPembimbing, 
            prosesBimbingan, 
            penguasaanMateriPembimbing,
            tataTulisLaporanPenguji, 
            kelengkapanMateriPenguji, 
            pencapaianTujuanPenguji, 
            penguasaanMateriPenguji, 
            presentasiPenguji
        );
    }

    @Override
    public List<Nilai> getNilaiPerSidang (int idSidang) {
        String sql = "SELECT namaKomponen, nilai FROM komponenNilaiSidang  n JOIN komponenNilai k on k.idKomponen = n.idKomponen WHERE idSidang = ? ORDER BY n.idKomponen";
        return jdbcTemplate.query(sql, this::mapRowToNilai, idSidang);
    }
    private Nilai mapRowToNilai(ResultSet resultSet, int rowNum) throws SQLException {
        return new Nilai (
            resultSet.getString("namaKomponen"),
            resultSet.getDouble("nilai")
        );
    }
}
