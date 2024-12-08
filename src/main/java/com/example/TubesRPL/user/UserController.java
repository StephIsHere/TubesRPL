package com.example.TubesRPL.user;

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

import com.example.TubesRPL.sidang.Sidang;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepository userRepo;

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
            session.setAttribute("npm", user.getNpm());
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
                return "koordinator/home";
            } else if (session.getAttribute("peran").equals("Dosen")) {
                return "dosen/home";
            } else if (session.getAttribute("peran").equals("Mahasiswa")) {
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
    //Kalau non-mahasiswa --> npm tidak bisa diisi
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
                          @RequestParam(required = false) String npm) {
        System.out.println("Peran: " + role);
        User user = new User();
        user.setNama(nama);
        user.setEmail(email);
        user.setPassword(password);
        user.setPeran(role);
        user.setNpm(npm);
        user.setStatus(true);

        userRepo.addUser(user);

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


    //KOORDINATOR----------------------------------------------
    //Bentuk BAP --> page html 
    // Komponen Nilai
    @GetMapping("/home/komponen-nilai")
    public String komponenNilai (HttpSession session, Model model){
        //Menampilkan nama dan peran
        String nama = (String)session.getAttribute("nama");
        String peran = (String)session.getAttribute("peran");
        model.addAttribute("nama", nama);
        model.addAttribute("peran", peran);
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("Koordinator")) {
            return "koordinator/komponenNilai";
        } else {
            return "redirect:/";
        }
    }

    //Tambah Sidang
    @GetMapping ("/home/addSidang")
    public String tambahSidangGet (HttpSession session, Model model){
        //Menampilkan nama dan peran
        String nama = (String)session.getAttribute("nama");
        String peran = (String)session.getAttribute("peran");
        model.addAttribute("namaKoor", nama);
        model.addAttribute("peran", peran);
        model.addAttribute("sidang", new Sidang());
        return "koordinator/AddSidang";
    }
    @GetMapping("/home/addSidang/getMahasiswaByNpm")
    @ResponseBody
    public Map<String, String> getMahasiswaByNpm(@RequestParam("npm") String npm) {
        Map<String, String> response = new HashMap<>();
        System.out.println("npm adalah" + npm);
        List<User> mahasiswa = this.userRepo.findByNpm(npm); 
        if (mahasiswa.isEmpty()) {
            response.put("nama", "Nama tidak ditemukan");
        }
        else{
            response.put("nama", mahasiswa.get(0).getNama());
        }
        return response;
    }
    @PostMapping ("/home/addSidang")
    public String tambahSidangPost(
        @RequestParam String npm,
        @RequestParam String namaMahasiswa,
        @RequestParam String judul,
        @RequestParam String pembimbingUtama,
        @RequestParam(required = false) String pembimbingPendamping,
        @RequestParam(required = false) String pengujiKetua,
        @RequestParam(required = false) String pengujiAnggota1,
        @RequestParam(required = false) String pengujiAnggota2,
        @RequestParam(required = false) String pengujiAnggota3
    ) {
        Sidang sidang = new Sidang();
        List<User> users = userRepo.findByNpm(npm);
        User mahasiswa = users.get(0);
        Long id = mahasiswa.getIdUser();
        sidang.setIdMahasiswa(id);
        sidang.setJudul(judul);

        // sidang.setNpm(npm);
        // sidang.setNamaMahasiswa(namaMahasiswa);
        // sidang.setJudul(judul);
        // sidang.setPembimbingUtama(pembimbingUtama);
        // sidang.setPembimbingPendamping(pembimbingPendamping);
        // sidang.setPengujiKetua(pengujiKetua);
        // sidang.setPengujiAnggota1(pengujiAnggota1);
        // sidang.setPengujiAnggota2(pengujiAnggota2);
        // sidang.setPengujiAnggota3(pengujiAnggota3);

        return "redirect:/home";
    }



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
}