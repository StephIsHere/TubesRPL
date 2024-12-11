package com.example.TubesRPL.komponenNilai;

import java.sql.SQLException;

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
        double penguasaanMateriPembimbing,
        int idKoordinator) throws SQLException {
            String sql = "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, 'bobot nilai', ?); " +
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Anggota Tim Penguji', ?, 'bobot nilai', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Pembimbing', ?, 'bobot nilai', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Koordinator', ?, 'bobot nilai', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Tata Tulis Laporan', ?, 'penguji', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Kelengkapan Materi', ?, 'penguji', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Pencapaian Tujuan', ?, 'penguji', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Penguasaan Materi', ?, 'penguji', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Presentasi', ?, 'penguji', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Tata Tulis Laporan', ?, 'pembimbing', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Kelengkapan Materi', ?, 'pembimbing', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Proses Bimbingan', ?, 'pembimbing', ?); "+
            "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator) VALUES ('Penguasaan Materi', ?, 'pembimbing', ?); ";
            jdbcTemplate.update(
                sql, bobotNilaiKetuaTimPenguji, idKoordinator, 
                bobotNilaiAnggotaTimPenguji, idKoordinator, 
                bobotNilaiPembimbing, idKoordinator, 
                bobotNilaiKoordinator, idKoordinator,
                tataTulisLaporanPenguji, idKoordinator,
                kelengkapanMateriPenguji, idKoordinator,
                pencapaianTujuanPenguji, idKoordinator,
                penguasaanMateriPenguji, idKoordinator,
                presentasiPenguji, idKoordinator,
                tataTulisLaporanPembimbing, idKoordinator,
                kelengkapanMateriPembimbing, idKoordinator,
                prosesBimbingan, idKoordinator,
                penguasaanMateriPembimbing, idKoordinator);
    }
}
