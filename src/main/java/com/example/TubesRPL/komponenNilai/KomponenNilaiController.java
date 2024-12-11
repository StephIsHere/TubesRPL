package com.example.TubesRPL.komponenNilai;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KomponenNilaiController {
    
    @PostMapping("saveKomponen")
    public String saveKomponen(
        @RequestParam int bobotNilaiKetuaTimPenguji,
        @RequestParam int bobotNilaiAnggotaTimPenguji,
        @RequestParam int bobotNilaiPembimbing,
        @RequestParam int bobotNilaiKoordinator,
        @RequestParam int tataTulisLaporanPenguji,
        @RequestParam int kelengkapanMateriPenguji,
        @RequestParam int pencapaianTujuanPenguji,
        @RequestParam int penguasaanMateriPenguji,
        @RequestParam int presentasiPenguji,
        @RequestParam int tataTulisLaporanPembimbing,
        @RequestParam int kelengkapanMateriPembimbing,
        @RequestParam int prosesBimbingan,
        @RequestParam int penguasaanMateriPembimbing)
        {
        
        return "index";
    }
}
