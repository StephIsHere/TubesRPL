package com.example.TubesRPL.komponenNilai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KomponenNilaiRepoJdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveKomponen (
        int bobotNilaiKetuaTimPenguji,
        int bobotNilaiAnggotaTimPenguji,
        int bobotNilaiPembimbing,
        int bobotNilaiKoordinator,
        int tataTulisLaporanPenguji,
        int kelengkapanMateriPenguji,
        int pencapaianTujuanPenguji,
        int penguasaanMateriPenguji,
        int presentasiPenguji,
        int tataTulisLaporanPembimbing,
        int kelengkapanMateriPembimbing,
        int prosesBimbingan,
        int penguasaanMateriPembimbing,
        int year,
        int idKoordinator) {

        // String sql = "INSERT INTO "komponenNilai" (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +;
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +
        // "INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tahunAkademik, tipe, idKoordinator) VALUES ('Ketua Tim Penguji', ?, ?, 'bobot nilai', ?); " +

    }
}
