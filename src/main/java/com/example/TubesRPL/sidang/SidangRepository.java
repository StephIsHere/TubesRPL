package com.example.TubesRPL.sidang;

import java.util.List;

public interface SidangRepository {
    List<Sidang> findSidang(int idSidang);
    void addSidang(Sidang sidang);
    List<Sidang> findAll();
}
