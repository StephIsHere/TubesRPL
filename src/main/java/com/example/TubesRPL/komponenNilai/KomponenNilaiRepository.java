package com.example.TubesRPL.komponenNilai;

import java.sql.SQLException;
import java.util.List;

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
        double penguasaanMateriPembimbing
    ) throws SQLException;
    public boolean checkKomponen();
    public List<KomponenNilai> getAll();
    public void editKomponen(
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
        double penguasaanMateriPembimbing
    ) throws SQLException;
    public double getBobot(int idKomponen);
    public List<Nilai> getNilaiPerSidang (int idSidang);
}
