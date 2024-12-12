package com.example.TubesRPL.sidang;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface SidangRepository {
    List<Sidang> findSidang(int idSidang);
    void addSidang(Sidang sidang);
    List<Sidang> findAll();
    List<Sidang> findSidangByJudul(String judul);
    List<Sidang> findAllSidangByIDmahasiswa(Long nik);
    List<Sidang> findAllSidangWithPenulis();
    List<Sidang> findAllSidangByIDdosen(Long idDosen);
}
