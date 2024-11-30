package com.example.TubesRPL.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String showLogin (Model model){
        if (!model.containsAttribute("error")) {
            model.addAttribute("error", null);
        }
        model.addAttribute("email", "");
        return "index";
    }

    @GetMapping("/home")
    public String showHome (HttpSession session, Model model) {
        if (session.getAttribute("idUser") != null) {
            String nama = (String)session.getAttribute("nama");
            String peran = (String)session.getAttribute("peran");
            model.addAttribute("nama", nama);
            model.addAttribute("peran", peran);
            System.out.println(session.getAttribute("nama"));
            System.out.println(session.getAttribute("peran"));
            if (session.getAttribute("peran").equals("Admin")) {
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

    @PostMapping("/")
    public String login (
        @RequestParam("email") String email, 
        @RequestParam("password") String password, 
        Model model, 
        HttpSession session) {
            
        List<User> users = userRepo.findUser(email, password);
        if (users.size() == 1) {
            User user = users.get(0);
            session.setAttribute("idUser", user.getIdUser());
            session.setAttribute("peran", user.getPeran());
            session.setAttribute("nama", user.getNama());            return "redirect:/home";
        } else {
            model.addAttribute("error", "email atau password salah");
            model.addAttribute("email", email);
            return "index";
        }
    }

    @GetMapping("/home/sidang1")
    public String showSidang1 (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("mahasiswa")) {
            return "mahasiswa/mahasiswaSidangBerlangsung";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/home/sidang2")
    public String showSidang2 (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("mahasiswa")) {
            return "mahasiswa/mahasiswaDetailSidangUpcoming";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/home/sidang3")
    public String showSidang3 (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("mahasiswa")) {
            return "mahasiswa/mahasiswaDetailSidangFinished";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/home/deleted")
    public String delete (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("admin")) {
            return "admin/adminPesertaDeleted";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/home/add")
    public String add (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("admin")) {
            return "admin/adminTambahPeserta";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/home/edit")
    public String edit (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("admin")) {
            return "admin/adminDetail";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/home/komponen-nilai")
    public String komponenNilai (HttpSession session){
        if (session.getAttribute("idUser") != null && session.getAttribute("peran").equals("koordinator")) {
            return "koordinator/komponenNilai";
        } else {
            return "redirect:/";
        }
    }
}
