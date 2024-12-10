package com.example.TubesRPL.sidang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        @RequestParam(required = false) String nikPembimbingPendamping,
        @RequestParam(required = false) String nikKetuaPenguji,
        @RequestParam(required = false) String nikAnggotaPenguji,
        HttpSession session
    ) {
        try {
            List<User> mahasiswaList = userRepo.findByNik(nik);
            if (mahasiswaList.isEmpty()) {
                throw new RuntimeException("Mahasiswa dengan NIK " + nik + " tidak ditemukan.");
            }
            User mahasiswa = mahasiswaList.get(0);

            Sidang sidang = new Sidang();
            sidang.setIdMahasiswa(mahasiswa.getIdUser());
            sidang.setJenisTA(jenisSidang);
            sidang.setTopik(topik);
            sidang.setJudul(judul);
            sidang.setTempat(tempat);
            sidang.setTanggal(LocalDate.parse(tanggal));
            sidang.setWaktu(LocalTime.parse(waktu));    
            sidang.setCatatan(null);
            sidang.setStatus("Upcoming");
            sidang.setTtdPembimbing1(null);
            sidang.setTtdPembimbing2(null);
            sidang.setTtdKetuaPenguji(null);
            sidang.setTtdTimPenguji(null);
            sidang.setTtdMahasiswa(null);
            sidang.setTtdKoordinator(null);

            Long koordinatorId = (Long) session.getAttribute("idUser");
            if (koordinatorId == null) {
                throw new RuntimeException("Koordinator ID tidak ditemukan di session");
            }
            sidang.setIdKoordinator(koordinatorId);

            sidangRepo.addSidang(sidang);

            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}