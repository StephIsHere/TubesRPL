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

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/sidang")
public class SidangController {
    @Autowired
    private SidangRepository sidangRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/AddSidang")
    public String tambahSidangPost(
        @RequestParam String nik,
        @RequestParam String jenisSidang,
        @RequestParam String topik,
        @RequestParam String judul,
        @RequestParam String tempat,
        @RequestParam String tanggal,
        @RequestParam String waktu,
        @RequestParam String nikPembimbingUtama,
        @RequestParam(required = false) String nikPembimbingPendamping,
        @RequestParam(required = false) String nikKetuaPenguji,
        @RequestParam(required = false) String nikAnggotaPenguji,
        HttpSession session
    ) {
        // Cari mahasiswa berdasarkan NIK
        List<User> mahasiswaList = userRepo.findByNik(nik);
        if (mahasiswaList.isEmpty()) {
            throw new RuntimeException("Mahasiswa dengan NIK " + nik + " tidak ditemukan.");
        }
        User mahasiswa = mahasiswaList.get(0);

        // // Cari pembimbing utama
        // List<User> pembimbingUtamaList = userRepo.findByNik(nikPembimbingUtama);
        // if (!pembimbingUtamaList.isEmpty()) {
        //     throw new RuntimeException("Pembimbing utama dengan NIK " + nik + " tidak ditemukan.");
        // }
        // User pembimbingUtama = pembimbingUtamaList.get(0);

        // // Cari pembimbing pendamping
        // User pembimbingPendamping = null;
        // if (nikPembimbingPendamping != null) {
        //     List<User> pembimbingPendampingList = userRepo.findByNik(nikPembimbingPendamping);
        //     if (!pembimbingPendampingList.isEmpty()) {
        //         pembimbingPendamping = pembimbingPendampingList.get(0);
        //     }
        // }

        // // Cari ketua penguji
        // User ketuaPenguji = null;
        // if (nikKetuaPenguji != null) {
        //     List<User> ketuaPengujiList = userRepo.findByNik(nikKetuaPenguji);
        //     if (!ketuaPengujiList.isEmpty()) {
        //         ketuaPenguji = ketuaPengujiList.get(0);
        //     }
        // }

        // // Cari anggota penguji
        // User anggotaPenguji = null;
        // if (nikAnggotaPenguji != null) {
        //     List<User> anggotaPengujiList = userRepo.findByNik(nikAnggotaPenguji);
        //     if (!anggotaPengujiList.isEmpty()) {
        //         anggotaPenguji = anggotaPengujiList.get(0);
        //     }
        // }
        // Buat instance Sidang
        Sidang sidang = new Sidang();
        sidang.setIdMahasiswa(mahasiswa.getIdUser());
        sidang.setJenisTA(jenisSidang);
        sidang.setTopik(topik);
        sidang.setJudul(judul);
        sidang.setTempat(tempat);
        sidang.setTanggal(LocalDate.parse(tanggal)); // Format harus yyyy-MM-dd
        sidang.setWaktu(LocalTime.parse(waktu)); // Format harus HH:mm
        sidang.setCatatan(null);  
        sidang.setStatus("Upcoming");  
        sidang.setTtdPembimbing1(null);
        sidang.setTtdPembimbing2(null);
        sidang.setTtdKetuaPenguji(null);
        sidang.setTtdTimPenguji(null);
        sidang.setTtdMahasiswa(null);
        sidang.setTtdKoordinator(null);
        sidang.setIdKoordinator((long)session.getAttribute("idUser"));
        System.out.println("GAJAH=" + (long) session.getAttribute("idUser"));

        // Simpan sidang ke database
        sidangRepo.addSidang(sidang);

        return "redirect:/home";
    }
}
