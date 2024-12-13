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
    private String namaPenulis;
    private String namaPembimbing1;
    private String namaPembimbing2; 
    private String namaPenguji1; 
    private String namaPenguji2; 
    private String namaKetuaPenguji; 
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
    public Sidang() {}   
}