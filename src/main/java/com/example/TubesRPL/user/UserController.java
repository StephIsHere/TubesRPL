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
    public String showLogin (){
        return "index";
    }

    @GetMapping("/home")
    public String showHome (HttpSession session) {
        if (session.getAttribute("idUser") != null) {
            if (session.getAttribute("peran").equals("admin")) {
                return "admin/adminPage";
            } else if (session.getAttribute("peran").equals("koordinator")) {
                return "koordinator/home";
            } else if (session.getAttribute("peran").equals("dosen")) {
                return "dosen/home";
            } else {
                return "mahasiswa/home";
            }
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/")
    public String login (@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
        List<User> users = userRepo.findUser(email, password);
        if (users.size() == 1) {
            User user = users.get(0);
            session.setAttribute("idUser", user.getIdUser());
            session.setAttribute("peran", user.getPeran());
            return "redirect:/home";
        } else {
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            return "index";
        }
    }
}
