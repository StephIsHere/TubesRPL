package com.example.TubesRPL.komponenNilai;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor

public class KomponenNilai {
    private int idKomponen;
    private String namaKomponen;
    private double bobotKomponen;
    private int tahunAkademik;
    private String tipe;
    private int idKoordinator;
}
