package com.example.TubesRPL.sidang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import com.example.TubesRPL.user.User;
import com.example.TubesRPL.user.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
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
        @RequestParam String nikPembimbingPendamping,
        @RequestParam String nikKetuaPenguji,
        @RequestParam String nikAnggotaPenguji,
        HttpSession session
    ) {
        // Cari mahasiswa berdasarkan NIK
        List<User> mahasiswaList = userRepo.findByNik(nik);
        User mahasiswa = mahasiswaList.get(0);

        List<User> pembimbing1 = userRepo.findByNik(nikPembimbingUtama);
        User pembimbingUtama = pembimbing1.get(0);

        List<User> pembimbing2 = userRepo.findByNik(nikPembimbingPendamping);
        User pembimbingPendamping = pembimbing2.get(0);

        List<User> penguji1 = userRepo.findByNik(nikPembimbingUtama);
        User ketuaPenguji = penguji1.get(0);

        List<User> penguji2 = userRepo.findByNik(nikPembimbingPendamping);
        User anggotaPenguji = penguji2.get(0);

        // Buat instance Sidang
        Sidang sidang = new Sidang();
        sidang.setJenisTA(jenisSidang);
        sidang.setTopik(topik);
        sidang.setNamaPenulis("");
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
        sidang.setIdMahasiswa(mahasiswa.getIdUser());
        sidang.setIdKoordinator((long)session.getAttribute("idUser"));

        // Simpan sidang ke database
        sidangRepo.addSidang(sidang);

        return "redirect:/home";
    }

    @PostMapping("/searchSidang")
    public String searchSidang(@RequestParam String judul, Model model, HttpSession session){
        List<Sidang> listSidang = this.sidangRepo.findSidangByJudul(judul);
        model.addAttribute("sidangs", listSidang);
        return "koordinator/home";
    }

    @PostMapping("/submitSidang")
    public String submitSidang(@RequestParam String judul, Model model, HttpSession session){
        Sidang sidang = this.sidangRepo.findSidangByJudul(judul).get(0);
        model.addAttribute("sidang", sidang);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        return "koordinator/DetailSidang";
    }
}
