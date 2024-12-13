package com.example.TubesRPL.user;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.TubesRPL.sidang.Sidang;
import com.example.TubesRPL.sidang.SidangRepository;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private SidangRepository sidangRepo;

    // Membuka halaman root --> login 
    // Handler jika ada salah email / password
    @GetMapping("/")
    public String showLogin(Model model){
        if (!model.containsAttribute("error")) {
            model.addAttribute("error", null);
        }
        model.addAttribute("email", "");
        return "index"; 
    }
    @PostMapping("/")
    public String login(
        @RequestParam("email") String email, 
        @RequestParam("password") String password, 
        Model model, 
        HttpSession session) {
        
        //findUser dengan email dan password yang ditulis user
        List<User> users = userRepo.findUser(email, password);
        if (users.size() == 1) {
            User user = users.get(0);
            //Ambil atribut dari user
            session.setAttribute("idUser", user.getIdUser());
            session.setAttribute("nama", user.getNama());  
            session.setAttribute("email", user.getEmail());          
            session.setAttribute("peran", user.getPeran());
            session.setAttribute("nik", user.getNik());
            session.setAttribute("status", user.getStatus());
            return "redirect:/home"; 
        } else { //Jika email / password salah
            model.addAttribute("error", "Email atau password salah");
            model.addAttribute("email", email);
            return "index"; 
        }
    }

    // Home page
    @GetMapping("/home")
    public String showHome(@RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String filter,
                           @RequestParam(defaultValue = "") String sort, HttpSession session, Model model) {
        if (session.getAttribute("idUser") != null) {
            // Simpan nama dan peran untuk html
            String nama = (String)session.getAttribute("nama");
            String peran = (String)session.getAttribute("peran");
            Long idMahasiswa = (Long)session.getAttribute("idUser");

            //nampilin ttd
            List<TandaTangan> ttdList = userRepo.getTtdByUserId(idMahasiswa);
            if (!ttdList.isEmpty()) {
                byte[] ttdBytes = ttdList.get(0).getTtd();
                String base64Image = Base64.getEncoder().encodeToString(ttdBytes);
                model.addAttribute("ttd", base64Image);
            } else {
                model.addAttribute("ttd", null);
            }
            
            model.addAttribute("nama", nama);
            model.addAttribute("peran", peran);
            model.addAttribute("query", name);
            model.addAttribute("filter", filter);
            model.addAttribute("sort", sort);
            
            if (session.getAttribute("peran").equals("Admin")) {
                List<User> allUsers = this.userRepo.findUserByName(name);
                if (sort.equals("true")) {
                    allUsers = this.userRepo.findAllDesc();
                } else if(sort.equals("false")){
                    allUsers = this.userRepo.findAll();
                }
                if (!filter.equals("")) {
                    allUsers = this.userRepo.findUserByRole(filter);
                }
                model.addAttribute("users", allUsers);
                return "admin/adminPage";

            } else if (session.getAttribute("peran").equals("Koordinator")) {
                // Ambil data sidang dari repository
                List<Sidang> sidangs = sidangRepo.findAllSidangWithPenulis(); 
                List<User> userList = userRepo.findAll();

                model.addAttribute("allUser", userList);
                model.addAttribute("sidangs", sidangs);

                return "koordinator/home";

            } else if (session.getAttribute("peran").equals("Dosen")) {
                List<Sidang> sidangs = sidangRepo.findAllSidangWithPenulis(); 
                List<User> userList = userRepo.findAll();

                model.addAttribute("allUser", userList);
                model.addAttribute("sidangs", sidangs);
                return "dosen/home";
            } else if (session.getAttribute("peran").equals("Mahasiswa")) {
                List<Sidang> sidangs = sidangRepo.findAllSidangByID(idMahasiswa); 
                List<User> userList = userRepo.findAll();

                model.addAttribute("allUser", userList);
                model.addAttribute("sidangs", sidangs);
                model.addAttribute("sidangs", sidangs);
                return "mahasiswa/mahasiswaMain";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }


    // ADMIN --------------------------------------- (-): edit user

    // input nama, email lgsg default buatkan --> nama@unpar
    //      jadi email tidak perlu diisi manual
    // Password default: --> otomatis 
    //      admin = admin
    //      koordinator = koord
    //      mahasiswa = nama
    //      dosen = dosen
    //USER BISA GANTI PASS ??

    //************KALAU UDA SUBMIT HARUS ADA OVERLAY "USER BERHASIL DITAMBAHKAN" *****************

    // Page menambahkan user
    @GetMapping("/home/add")
    public String showAddUserForm(Model model, HttpSession session) {
        //Menampilkan nama dan peran
        String nama = (String)session.getAttribute("nama");
        String peran = (String)session.getAttribute("peran");
        model.addAttribute("nama", nama);
        model.addAttribute("peran", peran);
        model.addAttribute("user", new User());
        return "admin/adminTambahPeserta";
    }
    @PostMapping("/home/add")
    public String addUser(@RequestParam String nama,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role,
                          @RequestParam(required = false) String nik) {
        System.out.println("Peran: " + role);
        User user = new User();
        user.setNama(nama);
        user.setEmail(email);
        user.setPassword(password);
        user.setPeran(role);
        user.setNik(nik);
        user.setStatus(true);

        userRepo.addUser(user);

        return "redirect:/home";
    }
    @PostMapping("/home/save")
    public String editUser(@RequestParam Long idUser,
                           @RequestParam String nama,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String role,
                           @RequestParam String nik) {
        User user = new User();
        user.setNama(nama);
        user.setEmail(email);
        user.setPassword(password);
        user.setPeran(role);
        user.setNik(nik);
        user.setStatus(true);
        user.setIdUser(idUser);
        userRepo.updateUser(user);

        return "redirect:/home";
    }


    //ADA OVERLAY --> APAKAH ANDA YAKIN INGIN MENONAKTIFKAN "nama"
    // Jadikan user tidak aktif
    @GetMapping("/home/deleted/{userId}")
    public String setUserInactive(@PathVariable Long userId, HttpSession session) {
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("Admin")) {
            userRepo.setUserInactive(userId);
            return "redirect:/home";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/home/searchSidang")
    public String searchUser(@RequestParam String judul, Model model, HttpSession session) {
        List<Sidang> listSidang = this.sidangRepo.findSidangByJudul(judul);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        model.addAttribute("sidangs", listSidang);
        model.addAttribute("nama", session.getAttribute("nama"));
        model.addAttribute("peran", session.getAttribute("peran"));
        if (session.getAttribute("nama").equals("Dosen")) {
            return"dosen/home";
        } else if (session.getAttribute("nama").equals("Mahasiswa")) {
            return "mahasiswa/mahasiswaMain";
        } else {
            return"/";
        }
    }

    //KOORDINATOR----------------------------------------------
    //Bentuk BAP --> page html 
    // Komponen Nilai
    // @GetMapping("/komponen-nilai")
    // public String komponenNilai (HttpSession session, Model model){
    //     //Menampilkan nama dan peran
    //     String nama = (String)session.getAttribute("nama");
    //     String peran = (String)session.getAttribute("peran");
    //     model.addAttribute("nama", nama);
    //     model.addAttribute("peran", peran);
    //     if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("Koordinator")) {
    //         return "koordinator/komponenNilai";
    //     } else {
    //         return "redirect:/";
    //     }
    // }

    //Tambah Sidang
    @GetMapping ("/home/addSidang")
    public String tambahSidangGet (HttpSession session, Model model){
        //Menampilkan nama dan peran
        String nama = (String)session.getAttribute("nama");
        String peran = (String)session.getAttribute("peran");
        model.addAttribute("namaKoor", nama);
        model.addAttribute("peran", peran);
        return "koordinator/AddSidang";
    }
    @GetMapping("/home/addSidang/getUserByNik")
    @ResponseBody
    public Map<String, String> getMahasiswaByNik(@RequestParam("nik") String nik) {
        Map<String, String> response = new HashMap<>();
        List<User> user = this.userRepo.findByNik(nik); 
        if (user.isEmpty()) {
            response.put("nama", "Nama tidak ditemukan");
        }
        else{
            response.put("nama", user.get(0).getNama());
        }
        return response;
    }
    // @PostMapping("/home/addSidang")
    // public String tambahSidangPost(
    //     @RequestParam String nik,
    //     @RequestParam String jenisTA,
    //     @RequestParam String topik,
    //     @RequestParam String judul,
    //     @RequestParam String tempat,
    //     @RequestParam String tanggal,
    //     @RequestParam String waktu,
    //     @RequestParam(required = false) String catatan,
    //     @RequestParam(required = false) String status,
    //     @RequestParam(required = false) byte[] bap,
    //     @RequestParam(required = false) byte[] ttdKetuaPenguji,
    //     @RequestParam(required = false) byte[] ttdTimPenguji,
    //     @RequestParam(required = false) byte[] ttdPembimbing1,
    //     @RequestParam(required = false) byte[] ttdPembimbing2,
    //     @RequestParam(required = false) byte[] ttdMahasiswa,
    //     @RequestParam(required = false) byte[] ttdKoordinator,
    //     @RequestParam(required = false) Long idKoordinator
    // ) {
    //     try {
    //         // Cari user berdasarkan NIK mahasiswa
    //         List<User> users = userRepo.findByNik(nik);
    //         if (users.isEmpty()) {
    //             throw new RuntimeException("Mahasiswa dengan NIK " + nik + " tidak ditemukan.");
    //         }
    //         User mahasiswa = users.get(0);

    //         // Buat instance Sidang
    //         Sidang sidang = new Sidang();
    //         sidang.setIdMahasiswa(mahasiswa.getIdUser());
    //         sidang.setJenisTA(jenisTA);
    //         sidang.setTopik(topik);
    //         sidang.setJudul(judul);
    //         sidang.setTempat(tempat);
    //         sidang.setTanggal(LocalDate.parse(tanggal)); // Format harus yyyy-MM-dd
    //         sidang.setWaktu(LocalTime.parse(waktu));     // Format harus HH:mm
    //         sidang.setCatatan(catatan);
    //         sidang.setStatus(status);
    //         sidang.setBap(bap);
    //         sidang.setTtdKetuaPenguji(ttdKetuaPenguji);
    //         sidang.setTtdTimPenguji(ttdTimPenguji);
    //         sidang.setTtdPembimbing1(ttdPembimbing1);
    //         sidang.setTtdPembimbing2(ttdPembimbing2);
    //         sidang.setTtdMahasiswa(ttdMahasiswa);
    //         sidang.setTtdKoordinator(ttdKoordinator);
    //         sidang.setIdKoordinator(idKoordinator);

    //         // Simpan sidang ke database
    //         sidangRepo.save(sidang);

    //         return "redirect:/home";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "error";
    //     }
    // }



    //MAHASISWA--- SALAH SEMUA
    // mahasiswa :
    // mahasiswa hanya ada 1 topik TA
    // Mahasiswa punya 1 / 2 box:
    //      1 box --> jika TA 1
    //      2 box --> jika TA 2 --> ada history TA 1 
    //      Keterangan box hanya Finished / Upcoming saja
    // Harus ambil ke database dahulu, mahasiswa ini lg TA 1 / TA 2
    // gausa nge serach, filter, sort --> karena hanya ada 2 box saja
    @GetMapping("/home/sidangTA1")
    public String showSidang1 (HttpSession session, Model model){
        //Menampilkan nama dan peran
        String nama = (String)session.getAttribute("nama");
        String peran = (String)session.getAttribute("peran");
        model.addAttribute("nama", nama);
        model.addAttribute("peran", peran);

        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/mahasiswaSidangBerlangsung";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/home/sidang2")
    public String showSidang2 (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/mahasiswaDetailSidangUpcoming";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/home/sidang3")
    public String showSidang3 (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("Mahasiswa")) {
            return "mahasiswa/mahasiswaDetailSidangFinished";
        } else {
            return "redirect:/";
        }
    }

    //submit ttd
    @PostMapping("uploadTtd")
    public String uploadttd (HttpSession session, @RequestParam("ttd") MultipartFile file) throws IOException {
        if (session.getAttribute("idUser") == null){
            return "redirect:/";
        }
        if (file.isEmpty()) {
            return "redirect:/";
        }
        Long idUser = (Long) session.getAttribute("idUser");
        byte[] ttd = file.getBytes();
        boolean success = userRepo.saveTtd(idUser, ttd);
        if (success) {
            return "redirect:/home";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("logout")
    public String logout (HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}