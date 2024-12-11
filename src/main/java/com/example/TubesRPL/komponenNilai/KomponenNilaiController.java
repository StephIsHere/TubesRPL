package com.example.TubesRPL.komponenNilai;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class KomponenNilaiController {
    @Autowired
    KomponenNilaiService service;
    
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
        @RequestParam int penguasaanMateriPembimbing,
        HttpSession session,
        Model model,
        RedirectAttributes redirectAttributes
    ) throws SQLException {
        Long idKoordinatorLong = (Long) session.getAttribute("idUser");
        int idKoordinator = idKoordinatorLong.intValue();
        int res = service.saveKomponen(bobotNilaiKetuaTimPenguji, bobotNilaiAnggotaTimPenguji, bobotNilaiPembimbing, bobotNilaiKoordinator, tataTulisLaporanPenguji, kelengkapanMateriPenguji, pencapaianTujuanPenguji, penguasaanMateriPenguji, presentasiPenguji, tataTulisLaporanPembimbing, kelengkapanMateriPembimbing, prosesBimbingan, penguasaanMateriPembimbing, idKoordinator);
        if (res == 0) {
            redirectAttributes.addFlashAttribute("status", "Komponen nilai berhasil disave");
        } else if (res == 1) {
            redirectAttributes.addFlashAttribute("status", "Total Bobot Nilai tidak 100%");
        } else if (res == 2) {
            redirectAttributes.addFlashAttribute("status", "Total Bobot Nilai Penguji tidak 100%");
        } else if (res == 3) {
            redirectAttributes.addFlashAttribute("status", "Total Bobot Nilai Pembimbing tidak 100%");
        } else {

        }
        
        return "redirect:/komponen-nilai";
    }
}
