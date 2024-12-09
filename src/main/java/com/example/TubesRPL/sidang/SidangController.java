package com.example.TubesRPL.sidang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import com.example.TubesRPL.user.User;


import com.example.TubesRPL.user.UserRepository;

@RestController
@RequestMapping("/sidang")
public class SidangController {
    @Autowired
    private SidangRepository sidangRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/add")
    public String tambahSidangPost(
        @RequestParam String nik,
        @RequestParam String jenisTA,
        @RequestParam String topik,
        @RequestParam String judul,
        @RequestParam String tempat,
        @RequestParam String tanggal,
        @RequestParam String waktu,
        @RequestParam(required = false) String catatan,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) byte[] bap,
        @RequestParam(required = false) byte[] ttdKetuaPenguji,
        @RequestParam(required = false) byte[] ttdTimPenguji,
        @RequestParam(required = false) byte[] ttdPembimbing1,
        @RequestParam(required = false) byte[] ttdPembimbing2,
        @RequestParam(required = false) byte[] ttdMahasiswa,
        @RequestParam(required = false) byte[] ttdKoordinator,
        @RequestParam(required = false) Long idKoordinator
    ) {
        try {
            // Cari user berdasarkan NIK mahasiswa
            List<User> users = userRepo.findByNik(nik);
            if (users.isEmpty()) {
                throw new RuntimeException("Mahasiswa dengan NIK " + nik + " tidak ditemukan.");
            }
            User mahasiswa = users.get(0);

            // Buat instance Sidang
            Sidang sidang = new Sidang();
            sidang.setIdMahasiswa(mahasiswa.getIdUser());
            sidang.setJenisTA(jenisTA);
            sidang.setTopik(topik);
            sidang.setJudul(judul);
            sidang.setTempat(tempat);
            sidang.setTanggal(LocalDate.parse(tanggal)); // Format harus yyyy-MM-dd
            sidang.setWaktu(LocalTime.parse(waktu));     // Format harus HH:mm
            sidang.setCatatan(catatan);
            sidang.setStatus(status);
            sidang.setBap(bap);
            sidang.setTtdKetuaPenguji(ttdKetuaPenguji);
            sidang.setTtdTimPenguji(ttdTimPenguji);
            sidang.setTtdPembimbing1(ttdPembimbing1);
            sidang.setTtdPembimbing2(ttdPembimbing2);
            sidang.setTtdMahasiswa(ttdMahasiswa);
            sidang.setTtdKoordinator(ttdKoordinator);
            sidang.setIdKoordinator(idKoordinator);

            // Simpan sidang ke database
            sidangRepo.addSidang(sidang);

            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
