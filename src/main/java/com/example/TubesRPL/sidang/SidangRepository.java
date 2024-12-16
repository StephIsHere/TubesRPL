package com.example.TubesRPL.sidang;

import java.util.List;
import java.util.Map;

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
    List<Sidang> findAllSidangWithIdUser(long iduser);
    void saveNilaiPenguji1(int idSidang, int ttl, int km, int pt, int p, int pm);
    void saveNilaiPenguji2(int idSidang, int ttl, int km, int pt, int p, int pm);
    void saveNilaiPembimbing(int idSidang, int ttl, int km, int pb, int pm);
    boolean checkNilaiPenguji1(int idSidang);
    boolean checkNilaiPenguji2(int idSidang);
    boolean checkNilaiPembimbing(int idSidang);
    void saveNilaiMain(int idSidang);
    byte[] getUserSignature(Long idUser);
    void saveSignatureToSidang(int idSidang, byte[] ttd, String role);
    Map<String, byte[]> getSignaturesBySidangId(int idSidang);
    String getRoleBySidangAndUser(int idSidang, Long idUser);
    void updateStatusSidang (int idSidang, String status);
}
