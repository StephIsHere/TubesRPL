package com.example.TubesRPL.komponenNilai;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KomponenNilaiService {
    @Autowired
    KomponenNilaiRepository repo;

    public int saveKomponen (
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
        int idKoordinator
    ) throws SQLException {
        // ubah ke decimal dan cek apakah 100%
        if (bobotNilaiKetuaTimPenguji + bobotNilaiAnggotaTimPenguji + bobotNilaiPembimbing + bobotNilaiKoordinator != 100) {
            return 1;
        }
        if (tataTulisLaporanPenguji + kelengkapanMateriPenguji + pencapaianTujuanPenguji + penguasaanMateriPenguji + presentasiPenguji != 100) {
            return 2;
        }
        if (tataTulisLaporanPembimbing + kelengkapanMateriPembimbing + prosesBimbingan + penguasaanMateriPembimbing != 100) {
            return 3;
        }

        double bobotNilaiKetuaTimPengujiD = bobotNilaiKetuaTimPenguji / 100.0;
        double bobotNilaiAnggotaTimPengujiD = bobotNilaiAnggotaTimPenguji / 100.0;
        double bobotNilaiPembimbingD = bobotNilaiPembimbing / 100.0;
        double bobotNilaiKoordinatorD = bobotNilaiKoordinator / 100.0;
 
        double tataTulisLaporanPengujiD = tataTulisLaporanPenguji / 100.0;
        double kelengkapanMateriPengujiD = kelengkapanMateriPenguji / 100.0;
        double pencapaianTujuanPengujiD = pencapaianTujuanPenguji / 100.0;
        double penguasaanMateriPengujiD = penguasaanMateriPenguji / 100.0;
        double presentasiPengujiD = presentasiPenguji / 100.0;

        double tataTulisLaporanPembimbingD = tataTulisLaporanPembimbing / 100.0;
        double kelengkapanMateriPembimbingD = kelengkapanMateriPembimbing / 100.0;
        double prosesBimbinganD = prosesBimbingan / 100.0;
        double penguasaanMateriPembimbingD = penguasaanMateriPembimbing / 100.0;

        repo.saveKomponen(
            bobotNilaiKetuaTimPengujiD,
            bobotNilaiAnggotaTimPengujiD,
            bobotNilaiPembimbingD,
            bobotNilaiKoordinatorD,
            tataTulisLaporanPengujiD,
            kelengkapanMateriPengujiD,
            pencapaianTujuanPengujiD,
            penguasaanMateriPengujiD,
            presentasiPengujiD,
            tataTulisLaporanPembimbingD,
            kelengkapanMateriPembimbingD,
            prosesBimbinganD,
            penguasaanMateriPembimbingD,
            idKoordinator
        );

        return 0;
    }
}
