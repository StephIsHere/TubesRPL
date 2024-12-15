package com.example.TubesRPL.sidang;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface SidangRepository {
    List<Sidang> findSidang(int idSidang);
    void addSidang(Sidang sidang);
    void addSidangDosen (int idSidang, Long nikPembimbing1, Long nikPembimbing2, Long nikPenguji1, Long nikPenguji2);
    List<Sidang> findAll();
    List<Sidang> findSidangByJudul(String judul);
    List<Sidang> findAllSidangByID(Long nik);
    List<Sidang> findAllSidangWithPenulis();
    Sidang addPengujiandPembimbing(String judul);
    boolean addCatatanSidang(Sidang sidang, String catatan);
}
