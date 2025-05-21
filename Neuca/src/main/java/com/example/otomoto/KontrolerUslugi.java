package com.example.otomoto;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Neuca/uslugi")
public class KontrolerUslugi {
    @RequestMapping("")
    public String uslugi(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/uslugi";
    }




    @RequestMapping("/alergologia")
    public String alergologia(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/alergologia";
    }

    @RequestMapping("/dermatologia")
    public String dermatologia(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/dermatologia";
    }



    @RequestMapping("/endokrynologia")
    public String endokrynologia(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/endokrynologia";
    }


    @RequestMapping("/ginekologia")
    public String ginekologia(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/ginekologia";
    }


    @RequestMapping("/interna")
    public String interna(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/interna";
    }

    @RequestMapping("/kardiologia")
    public String kardiologia(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/kardiologia";
    }


    @RequestMapping("/laryngologia")
    public String laryngologia(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/laryngologia";
    }

    @RequestMapping("/okulistyka")
    public String okulistyka(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/okulistyka";
    }


    @RequestMapping("/stomatologia")
    public String stomatologia(Authentication authentication, Model model){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("log", isLogged);
        return "/uslugi/stomatologia";
    }










}
