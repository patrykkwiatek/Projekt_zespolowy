package com.example.otomoto;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/Neuca")
public class KontrolerLog {
    private final SerwisMyUser serwisMyUser;
    private final SerwisKoszyk serwisKoszyk;

    public KontrolerLog(SerwisMyUser serwisMyUser, SerwisKoszyk serwisKoszyk) {
        this.serwisMyUser = serwisMyUser;
        this.serwisKoszyk=serwisKoszyk;
    }



    @RequestMapping("/mojProfilPacjent")
    public String mojProfilPacjent(Model model,Authentication authentication){
        List<RodzajLeku> leki = Arrays.asList(RodzajLeku.values());
        List<LekarzSpec> lekarze = Arrays.asList(LekarzSpec.values());
        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);
        List<Zamowienie> zamowienia =serwisKoszyk.getZamowienia(user);
        model.addAttribute("zamowienia",zamowienia);
        model.addAttribute("lekarze",lekarze);
        model.addAttribute("leki", leki);
        model.addAttribute("user", user);

        return "mojProfilPacjent";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/rejestracjaPacjent")
    public String rejestracjaPacjent(MyUserDTO myUserDTO){
        return "rejestracjaPacjent";
    }

    @RequestMapping("/rejestracjaLekarz")
    public String rejestracjaLekarz(MyUserDTO myUserDTO, Model model){
        List<LekarzSpec> lekarze = Arrays.asList(LekarzSpec.values());
        model.addAttribute("lekarze",lekarze);
        return "rejestracjaLekarz";
    }

    @RequestMapping("/rejestracjaAptekarz")
    public String rejestracjaAptekarz(MyUserDTO myUserDTO){
        return "rejestracjaAptekarz";
    }

    @RequestMapping("/addUserPacjent")
    public String dodanoUserPacjent(Authentication authentication,MyUserDTO myUserDTO, BindingResult bindingResult, Model model){
        boolean isLogged;
        if (authentication != null && authentication.isAuthenticated()) {
            isLogged=true;
            model.addAttribute("log",isLogged);
        } else {
            isLogged=false;
            model.addAttribute("log",isLogged);
        }

        if(bindingResult.hasErrors()){
            return "rejestracjaPacjent";
        }
        if(!myUserDTO.getPassword().equals(myUserDTO.getConfirm())){
            bindingResult.rejectValue("confirm", "user.haslo3","hasla sa rozne");
            return "rejestracjaPacjent";
        }



        if(serwisMyUser.addUserPacjent(myUserDTO)){
            List<RodzajLeku> leki = Arrays.asList(RodzajLeku.values());
            model.addAttribute("leki", leki);
            List<LekarzSpec> lekarze = Arrays.asList(LekarzSpec.values());
            model.addAttribute("lekarze",lekarze);
            return "dodanoUser";
        } else{
            bindingResult.rejectValue("username","user.login3","uzytkownik o takiej nazwie juz istnieje");
            return "rejestracjaPacjent";
        }
    }


    @RequestMapping("/addUserLekarz")
    public String addUserLekarz(Authentication authentication,MyUserDTO myUserDTO, BindingResult bindingResult, Model model){
        boolean isLogged;
        if (authentication != null && authentication.isAuthenticated()) {
            isLogged=true;
            model.addAttribute("log",isLogged);
        } else {
            isLogged=false;
            model.addAttribute("log",isLogged);
        }
        if(bindingResult.hasErrors()){
            return "rejestracjaLekarz";
        }
        if(!myUserDTO.getPassword().equals(myUserDTO.getConfirm())){
            bindingResult.rejectValue("confirm", "user.haslo3","hasla sa rozne");
            return "rejestracjaLekarz";
        }
        if(serwisMyUser.addUserLekarz(myUserDTO)){
            List<RodzajLeku> leki = Arrays.asList(RodzajLeku.values());
            model.addAttribute("leki", leki);
            List<LekarzSpec> lekarze = Arrays.asList(LekarzSpec.values());
            model.addAttribute("lekarze",lekarze);
            return "dodanoUser";
        } else{
            bindingResult.rejectValue("username","user.login3","uzytkownik o takiej nazwie juz istnieje");
            List<LekarzSpec> lekarze = Arrays.asList(LekarzSpec.values());
            model.addAttribute("lekarze",lekarze);
            return "rejestracjaLekarz";
        }
    }



    @RequestMapping("/addUserAptekarz")
    public String dodanoUserAptekarz(Authentication authentication,MyUserDTO myUserDTO, BindingResult bindingResult, Model model){
        boolean isLogged;
        if (authentication != null && authentication.isAuthenticated()) {
            isLogged=true;
            model.addAttribute("log",isLogged);
        } else {
            isLogged=false;
            model.addAttribute("log",isLogged);
        }
        if(bindingResult.hasErrors()){
            return "rejestracjaAptekarz";
        }
        if(!myUserDTO.getPassword().equals(myUserDTO.getConfirm())){
            bindingResult.rejectValue("confirm", "user.haslo3","hasla sa rozne");
            return "rejestracjaAptekarz";
        }
        if(serwisMyUser.addUserAptekarz(myUserDTO)){
            List<RodzajLeku> leki = Arrays.asList(RodzajLeku.values());
            model.addAttribute("leki", leki);
            List<LekarzSpec> lekarze = Arrays.asList(LekarzSpec.values());
            model.addAttribute("lekarze",lekarze);
            return "dodanoUser";
        } else{
            bindingResult.rejectValue("username","user.login3","uzytkownik o takiej nazwie juz istnieje");
            return "rejestracjaAptekarz";
        }
    }


    @RequestMapping("/logout")
    public String logout(){
        return "logout";
    }





}
