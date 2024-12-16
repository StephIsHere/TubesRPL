package com.example.TubesRPL.sidang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.example.TubesRPL.komponenNilai.KomponenNilai;
import com.example.TubesRPL.komponenNilai.KomponenNilaiRepoJdbc;
import com.example.TubesRPL.komponenNilai.Nilai;
import com.example.TubesRPL.user.TandaTangan;
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

    @Autowired
    private KomponenNilaiRepoJdbc nilaiRepo;

    @Autowired
    private GenerateBAP bap;

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

        // List<User> pembimbing1 = userRepo.findByNik(nikPembimbingUtama);
        // User pembimbingUtama = pembimbing1.get(0);

        // List<User> pembimbing2 = userRepo.findByNik(nikPembimbingPendamping);
        // User pembimbingPendamping = pembimbing2.get(0);

        // List<User> penguji1 = userRepo.findByNik(nikPembimbingUtama);
        // User ketuaPenguji = penguji1.get(0);

        // List<User> penguji2 = userRepo.findByNik(nikPembimbingPendamping);
        // User anggotaPenguji = penguji2.get(0);

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
        sidang.setStatus("Belum Dimulai"); 
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

        List<User> pembimbingUt = userRepo.findByNik(nikPembimbingUtama);
        long idPemUt = pembimbingUt.get(0).getIdUser();

        List<User> pembimbingPen = userRepo.findByNik(nikPembimbingPendamping);
        long idPemPen = pembimbingPen.get(0).getIdUser();

        List<User> ketPeng = userRepo.findByNik(nikKetuaPenguji);
        long idketPeng = ketPeng.get(0).getIdUser();

        List<User> angPeng = userRepo.findByNik(nikAnggotaPenguji);
        long idAngPeng = angPeng.get(0).getIdUser();

        
        List<Sidang> sidang2= sidangRepo.findSidangByJudul(judul);
        int idSidang = sidang2.get(0).getIdSidang();
        System.out.println("harimau" + idSidang);

        sidangRepo.addSidangDosen(idSidang, idPemUt, idPemPen, idketPeng, idAngPeng);


        return "redirect:/home";
    }

    @PostMapping("/searchSidang")
    public String searchSidang(@RequestParam String judul, Model model, HttpSession session){
        List<Sidang> listSidang = this.sidangRepo.findSidangByJudul(judul);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        model.addAttribute("sidangs", listSidang);
        if (session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/home";
        } else if (session.getAttribute("peran").equals("Dosen")) {
            return "dosen/home";
        } else if (session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/home";
        }
        return"redirect:/";
    }

    @PostMapping("/detailSidang")
    public String submitSidang(@RequestParam String judul, Model model, HttpSession session){
        Sidang sidang = this.sidangRepo.addPengujiandPembimbing(judul);
        List<KomponenNilai> listNilai = this.nilaiRepo.getAll();
        List<Nilai> nilaiSidang = nilaiRepo.getNilaiPerSidang(sidang.getIdSidang());
        //nampilin ttd
        Long idUser = (Long)session.getAttribute("idUser");
        List<TandaTangan> ttdList = userRepo.getTtdByUserId(idUser);
        if (!ttdList.isEmpty()) {
            byte[] ttdBytes = ttdList.get(0).getTtd();
            String base64Image = Base64.getEncoder().encodeToString(ttdBytes);
            model.addAttribute("ttd", base64Image);
        } else {
            model.addAttribute("ttd", null);
        }
        model.addAttribute("nilaiAsli", nilaiSidang);
        model.addAttribute("sidang", sidang);
        model.addAttribute("listNilai", listNilai);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        if (session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/DetailSidang";
        } else if (session.getAttribute("peran").equals("Dosen")) {
            return "dosen/DetailSidang";
        } else if (session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/DetailSidang";
        }
        return"redirect:/";
    }

    // perlu perbaikan buat ngasih tau kalo dia salah ato engga pake variabel res
    @PostMapping("/submitCatatanSidang")
    public String submitCatatan(@RequestParam String catatan,@RequestParam String judul, Model model, HttpSession session){
        Sidang sidang = this.sidangRepo.addPengujiandPembimbing(judul);
        boolean res = this.sidangRepo.addCatatanSidang(sidang, catatan);
        return "redirect:/home";
    }

    @PostMapping("/generateBAP")
    public String generateBAP(@RequestParam String judul, Model model, HttpSession session) {
        System.out.println(judul);
        Sidang sidang = this.sidangRepo.addPengujiandPembimbing(judul);
        System.out.println(sidang);
        bap.generate(sidang.getIdSidang());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/sidang/bap.pdf";
    }


    //tempel ttd ke pdf DISINI KERJAINNYA PEN
    @PostMapping("/setujuBAP")
    public String setujuBAP(@RequestParam String judul, Model model, HttpSession session) {
        Sidang sidang = this.sidangRepo.addPengujiandPembimbing(judul);
        List<KomponenNilai> listNilai = this.nilaiRepo.getAll();
        model.addAttribute("sidang", sidang);
        model.addAttribute("listNilai", listNilai);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        if (session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/DetailSidang";
        } else if (session.getAttribute("peran").equals("Dosen")) {
            return "dosen/DetailSidang";
        } else if (session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/DetailSidang";
        }
        return"redirect:/";
    }

    @PostMapping("/submitNilaiPenguji1")
    public String submitNilaiPenguji1(@RequestParam String judul, @RequestParam int ttl, @RequestParam int km, @RequestParam int pt, @RequestParam int p, @RequestParam int pm, HttpSession session, Model model) {
        Sidang sidang = this.sidangRepo.addPengujiandPembimbing(judul);
        List<KomponenNilai> listNilai = this.nilaiRepo.getAll();

        boolean exist = sidangRepo.checkNilaiPenguji1(sidang.getIdSidang());
        if (!exist) {
            sidangRepo.saveNilaiPenguji1(sidang.getIdSidang(), ttl, km, pt, p, pm);
        }
        sidangRepo.saveNilaiMain(sidang.getIdSidang());

        model.addAttribute("sidang", sidang);
        model.addAttribute("listNilai", listNilai);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        if (session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/DetailSidang";
        } else if (session.getAttribute("peran").equals("Dosen")) {
            return "dosen/DetailSidang";
        } else if (session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/DetailSidang";
        }
        return"redirect:/";

    }

    @PostMapping("/submitNilaiPenguji2")
    public String submitNilaiPenguji2(@RequestParam String judul, @RequestParam int ttl, @RequestParam int km, @RequestParam int pt, @RequestParam int p, @RequestParam int pm, HttpSession session, Model model) {
        Sidang sidang = this.sidangRepo.addPengujiandPembimbing(judul);
        List<KomponenNilai> listNilai = this.nilaiRepo.getAll();

        boolean exist = sidangRepo.checkNilaiPenguji2(sidang.getIdSidang());
        if (!exist) {
            sidangRepo.saveNilaiPenguji2(sidang.getIdSidang(), ttl, km, pt, p, pm);
        }
        sidangRepo.saveNilaiMain(sidang.getIdSidang());

        model.addAttribute("sidang", sidang);
        model.addAttribute("listNilai", listNilai);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        if (session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/DetailSidang";
        } else if (session.getAttribute("peran").equals("Dosen")) {
            return "dosen/DetailSidang";
        } else if (session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/DetailSidang";
        }
        return"redirect:/";

    }

    @PostMapping("/submitNilaiPembimbing")
    public String submitNilaiPembimbing(@RequestParam String judul, @RequestParam int ttl, @RequestParam int km, @RequestParam int pb, @RequestParam int pm, HttpSession session, Model model) {
        Sidang sidang = this.sidangRepo.addPengujiandPembimbing(judul);
        List<KomponenNilai> listNilai = this.nilaiRepo.getAll();

        boolean exist = sidangRepo.checkNilaiPembimbing(sidang.getIdSidang());
        if (!exist) {
            sidangRepo.saveNilaiPembimbing(sidang.getIdSidang(), ttl, km, pb, pm);
        }
        sidangRepo.saveNilaiMain(sidang.getIdSidang());
        
        model.addAttribute("sidang", sidang);
        model.addAttribute("listNilai", listNilai);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        if (session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/DetailSidang";
        } else if (session.getAttribute("peran").equals("Dosen")) {
            return "dosen/DetailSidang";
        } else if (session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/DetailSidang";
        }
        return"redirect:/";

    }
}
