package com.example.TubesRPL.komponenNilai;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public interface KomponenNilaiRepository {
    public void saveKomponen(
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
        int idKoordinator
    ) throws SQLException;
}
