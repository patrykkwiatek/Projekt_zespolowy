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
    private final SerwisRezerwacjaLeku serwisRezerwacjaLeku;

    public KontrolerLog(SerwisRezerwacjaLeku serwisRezerwacjaLeku, SerwisMyUser serwisMyUser, SerwisKoszyk serwisKoszyk) {
        this.serwisMyUser = serwisMyUser;
        this.serwisKoszyk=serwisKoszyk;
        this.serwisRezerwacjaLeku=serwisRezerwacjaLeku;
    }



    @RequestMapping("/mojProfilPacjent")
    public String mojProfilPacjent(Model model,Authentication authentication){
        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);
        List<Zamowienie> zamowienia =serwisKoszyk.getZamowienia(user);
        List<RezerwacjaLeku> lista=serwisRezerwacjaLeku.zwrocRezerwacjaUzytkownika(user);
        int ile=lista.size();
        List<RezerwacjaLeku> listaR=new ArrayList<>();
        if(ile==1){
            listaR.add(lista.get(0));
        }else if(ile>=2){
            listaR.add(lista.get(0));
            listaR.add(lista.get(1));
        }
        if(ile==0){
            model.addAttribute("czyTabela",false);
        }else if(ile>=1){
            model.addAttribute("czyTabela",true);
            model.addAttribute("lista",listaR);
        }
        model.addAttribute("zamowienia",zamowienia);
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
