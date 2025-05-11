package com.example.otomoto;


import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/Neuca")
@Controller
public class KontrolerApteka {
    SerwisMyUser serwisMyUser;
    SerwisApteka serwisApteka;

    public KontrolerApteka(SerwisMyUser serwisMyUser, SerwisApteka serwisApteka) {
        this.serwisMyUser = serwisMyUser;
        this.serwisApteka=serwisApteka;
    }

    @RequestMapping("/strefaAptekarza")
    public String strefaAptekarza(Authentication authentication){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        if(apteka==null){
            return "redirect:/Neuca/strefaAptekarza/utworzApteke";
        }
        if(apteka.isPotwierdzenie()){
            System.out.println("apteka jest potwierdzona");
            return "strefaAptekarza";

        }else{
            System.out.println("apteka nie jest potwierdzona");
            return "redirect:/Neuca/strefaAptekarza/potwierdzApteka";
        }
    }


    @RequestMapping("/strefaAptekarza/potwierdzApteka")
    public String potwierdzApteka(){
        return "potwierdzApteka";
    }

    @RequestMapping("/strefaAptekarza/utworzApteke")
    public String utworzApteke(Model model, Apteka apteka){
        List<Wojewodztwo> wojewodztwa= new ArrayList<>(Arrays.asList(Wojewodztwo.values()));
        wojewodztwa.remove(Wojewodztwo.ALL);
        model.addAttribute("woje",wojewodztwa);
        return "utworzApteke";
    }

    @RequestMapping("/strefaAptekarza/utworzonoApteke")
    public String utworzonoApteke(Apteka apteka,Authentication authentication){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        serwisApteka.dodajApteke(myUser,apteka);
        return "utworzonoApteke";

    }

}
