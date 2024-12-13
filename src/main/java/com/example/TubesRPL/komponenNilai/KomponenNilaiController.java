package com.example.TubesRPL.komponenNilai;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        int res = 0;
        boolean isThereKomponen = service.checkKomponen();
        // Long idKoordinatorLong = (Long) session.getAttribute("idUser");
        // int idKoordinator = idKoordinatorLong.intValue();
        if (isThereKomponen) {
            res = service.editKomponen(bobotNilaiKetuaTimPenguji, bobotNilaiAnggotaTimPenguji, bobotNilaiPembimbing, bobotNilaiKoordinator, tataTulisLaporanPenguji, kelengkapanMateriPenguji, pencapaianTujuanPenguji, penguasaanMateriPenguji, presentasiPenguji, tataTulisLaporanPembimbing, kelengkapanMateriPembimbing, prosesBimbingan, penguasaanMateriPembimbing);

        } else {
            res = service.saveKomponen(bobotNilaiKetuaTimPenguji, bobotNilaiAnggotaTimPenguji, bobotNilaiPembimbing, bobotNilaiKoordinator, tataTulisLaporanPenguji, kelengkapanMateriPenguji, pencapaianTujuanPenguji, penguasaanMateriPenguji, presentasiPenguji, tataTulisLaporanPembimbing, kelengkapanMateriPembimbing, prosesBimbingan, penguasaanMateriPembimbing);
        }
        if (res == 0) {
            redirectAttributes.addFlashAttribute("statusB", "Komponen nilai berhasil disave");
        } else {
            redirectAttributes.addFlashAttribute("bobotNilaiKetuaTimPenguji", bobotNilaiKetuaTimPenguji);
            redirectAttributes.addFlashAttribute("bobotNilaiAnggotaTimPenguji", bobotNilaiAnggotaTimPenguji);
            redirectAttributes.addFlashAttribute("bobotNilaiPembimbing", bobotNilaiPembimbing);
            redirectAttributes.addFlashAttribute("bobotNilaiKoordinator", bobotNilaiKoordinator);
            redirectAttributes.addFlashAttribute("tataTulisLaporanPenguji", tataTulisLaporanPenguji);
            redirectAttributes.addFlashAttribute("kelengkapanMateriPenguji", kelengkapanMateriPenguji);
            redirectAttributes.addFlashAttribute("pencapaianTujuanPenguji", pencapaianTujuanPenguji);
            redirectAttributes.addFlashAttribute("penguasaanMateriPenguji", penguasaanMateriPenguji);
            redirectAttributes.addFlashAttribute("presentasiPenguji", presentasiPenguji);
            redirectAttributes.addFlashAttribute("tataTulisLaporanPembimbing", tataTulisLaporanPembimbing);
            redirectAttributes.addFlashAttribute("kelengkapanMateriPembimbing", kelengkapanMateriPembimbing);
            redirectAttributes.addFlashAttribute("prosesBimbingan", prosesBimbingan);
            redirectAttributes.addFlashAttribute("penguasaanMateriPembimbing", penguasaanMateriPembimbing);
            if (res == 1) {
                redirectAttributes.addFlashAttribute("status", "Total Bobot Nilai tidak 100%");
            } else if (res == 2) {
                redirectAttributes.addFlashAttribute("status", "Total Bobot Nilai Penguji tidak 100%");
            } else if (res == 3) {
                redirectAttributes.addFlashAttribute("status", "Total Bobot Nilai Pembimbing tidak 100%");
            }
        }
        
        return "redirect:/komponen-nilai";
    }

    @GetMapping("/komponen-nilai")
    public String komponenNilai (HttpSession session, Model model){
        //Menampilkan nama dan peran
        boolean isKomponenAvailable = service.checkKomponen();
        String nama = (String)session.getAttribute("nama");
        String peran = (String)session.getAttribute("peran");
        model.addAttribute("nama", nama);
        model.addAttribute("peran", peran);

        if (!isKomponenAvailable) {
            model.addAttribute("bobotNilaiKetuaTimPengujiT", "0");
            model.addAttribute("bobotNilaiAnggotaTimPengujiT", "0");
            model.addAttribute("bobotNilaiPembimbingT", "0");
            model.addAttribute("bobotNilaiKoordinatorT", "0");
            model.addAttribute("tataTulisLaporanPengujiT", "0");
            model.addAttribute("kelengkapanMateriPengujiT", "0");
            model.addAttribute("pencapaianTujuanPengujiT", "0");
            model.addAttribute("penguasaanMateriPengujiT", "0");
            model.addAttribute("presentasiPengujiT", "0");
            model.addAttribute("tataTulisLaporanPembimbingT", "0");
            model.addAttribute("kelengkapanMateriPembimbingT", "0");
            model.addAttribute("prosesBimbinganT", "0");
            model.addAttribute("penguasaanMateriPembimbingT", "0");
        } else {
            List<KomponenNilai> komponen = service.getAll();
            model.addAttribute("bobotNilaiKetuaTimPengujiT", komponen.get(0).getBobotKomponen()*100);
            model.addAttribute("bobotNilaiAnggotaTimPengujiT", komponen.get(1).getBobotKomponen()*100);
            model.addAttribute("bobotNilaiPembimbingT", komponen.get(2).getBobotKomponen()*100);
            model.addAttribute("bobotNilaiKoordinatorT", komponen.get(3).getBobotKomponen()*100);
            model.addAttribute("tataTulisLaporanPengujiT", komponen.get(4).getBobotKomponen()*100);
            model.addAttribute("kelengkapanMateriPengujiT", komponen.get(5).getBobotKomponen()*100);
            model.addAttribute("pencapaianTujuanPengujiT", komponen.get(6).getBobotKomponen()*100);
            model.addAttribute("penguasaanMateriPengujiT", komponen.get(7).getBobotKomponen()*100);
            model.addAttribute("presentasiPengujiT", komponen.get(8).getBobotKomponen()*100);
            model.addAttribute("tataTulisLaporanPembimbingT", komponen.get(9).getBobotKomponen()*100);
            model.addAttribute("kelengkapanMateriPembimbingT", komponen.get(10).getBobotKomponen()*100);
            model.addAttribute("prosesBimbinganT", komponen.get(11).getBobotKomponen()*100);
            model.addAttribute("penguasaanMateriPembimbingT", komponen.get(12).getBobotKomponen()*100);
        }

        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/komponenNilai";
        } else {
            return "redirect:/";
        }
    }
}
