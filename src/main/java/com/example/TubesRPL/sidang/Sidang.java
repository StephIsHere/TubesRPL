package com.example.TubesRPL.sidang;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor

public class Sidang {
    
    private int idSidang;
    private String jenisTA;
    private String topik;
    private String judul;
    private String tempat;
    private LocalDate tanggal;
    private LocalTime waktu;
    private String catatan;
    private String status;
    private byte[] ttdKetuaPenguji;
    private byte[] ttdTimPenguji;
    private byte[] ttdPembimbing1;
    private byte[] ttdPembimbing2;
    private byte[] ttdMahasiswa;
    private byte[] ttdKoordinator;
    private Long idKoordinator;
    private Long idMahasiswa;
    private Long idPembimbing1;
    private Long idPembimbing2;
    private Long idPenguji1;
    private Long idPenguji2;


    public Sidang() {}

    
}