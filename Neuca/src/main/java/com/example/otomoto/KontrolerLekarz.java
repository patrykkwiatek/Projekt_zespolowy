package com.example.otomoto;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/Neuca")
@Controller
public class KontrolerLekarz {
    SerwisLekarz serwisLekarz;
    SerwisMyUser serwisMyUser;


    KontrolerLekarz(SerwisLekarz serwisLekarz,SerwisMyUser serwisMyUser){
        this.serwisLekarz=serwisLekarz;
        this.serwisMyUser=serwisMyUser;
    }


    @RequestMapping("/strefaLekarza")
    public String strefaLekarza(Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/Neuca/login";
        }
        String username = authentication.getName();
        MyUser myUser = serwisMyUser.zwrocUser(username);
        Lekarz lekarz = myUser.getLekarz();
        if (lekarz == null) {
            System.out.println("lekarz to null");
        }
        else {
            System.out.println("lekarz.isPotwierdzenie(): " + lekarz.isPotwierdzenie());
        }
        if(lekarz != null && lekarz.isPotwierdzenie()==false){
            return "redirect:/Neuca/strefaLekarza/potwierdzLekarza";
        }

        if (lekarz == null && serwisMyUser.czyMaROle("LEKARZ", myUser)) {
            return "redirect:/Neuca/strefaLekarza/UtworzGabinet";
        }
        if(!serwisMyUser.czyMaROle("LEKARZ",myUser)){
            return "redirect:/Neuca/strefaLekarza/brakUprawnien";
        }
        return "strefaLekarza";
    }

    @RequestMapping("/strefaLekarza/UtworzGabinet")
    public String utworzGabinet(Lekarz lekarz){
        return"UtworzGabinet";
    }

    @RequestMapping("/strefaLekarza/utworzonoGabinet")
    public String utworzonoGabinet(Lekarz lekarz, Model model,Authentication authentication){
        String username = authentication.getName();
        MyUser myUser = serwisMyUser.zwrocUser(username);
        Lekarz lekarz1=serwisLekarz.dodajLekarza(lekarz,myUser);
        return "utworzonoGabinet";
    }

    @RequestMapping("/strefaLekarza/potwierdzLekarza")
    public String potwierdzLekarza(){
        return "potwierdzLekarza";
    }

    @RequestMapping("/strefaLekarza/brakUprawnien")
    public String brakUprawnien(){
        return  "brakUprawnien";
    }


    @RequestMapping("/strefaLekarza/ustalGodziny")
    public String ustalGodziny(Authentication authentication, Model model){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        model.addAttribute("id",myUser.getId());
        return "ustalGodziny";
    }

}
