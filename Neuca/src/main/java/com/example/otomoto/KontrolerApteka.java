package com.example.otomoto;


import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
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


    @RequestMapping("/strefaAptekarza/godzinyApteka")
    public String godzinyApteka(Authentication authentication, Model model){
        MyUser myUser=serwisMyUser.zwrocUser(authentication.getName());
        Apteka apteka=myUser.getApteka();
        model.addAttribute("pon", apteka.isPon());
        model.addAttribute("wt", apteka.isWt());
        model.addAttribute("sr", apteka.isSr());
        model.addAttribute("czw", apteka.isCzw());
        model.addAttribute("pt", apteka.isPt());
        model.addAttribute("sob", apteka.isSb());
        model.addAttribute("ndz", apteka.isNdz());
        model.addAttribute("ponstart", apteka.getPonStart());
        model.addAttribute("ponstop", apteka.getPonStop());
        model.addAttribute("wtstart", apteka.getWtStart());
        model.addAttribute("wtstop", apteka.getWtStop());
        model.addAttribute("srstart", apteka.getSrStart());
        model.addAttribute("srstop", apteka.getSrStop());
        model.addAttribute("czwstart", apteka.getCzwStart());
        model.addAttribute("czwstop", apteka.getCzwStop());
        model.addAttribute("ptstart", apteka.getPtStart());
        model.addAttribute("ptstop", apteka.getPtStop());
        model.addAttribute("sobstart", apteka.getSbStart());
        model.addAttribute("sobstop", apteka.getSbStop());
        model.addAttribute("ndzstart", apteka.getNdzStart());
        model.addAttribute("ndzstop", apteka.getNdzStop());

        return "godzinyApteka";
    }


    @RequestMapping("/strefaAptekarza/edytujGodzinyApteka")
    public String edytujGodzinyApteka(Model model,Authentication authentication,
                                      @RequestParam int liczba){
        MyUser myUser=serwisMyUser.zwrocUser(authentication.getName());
        Apteka apteka=myUser.getApteka();
        if (liczba == 1) {
            model.addAttribute("dzien", "poniedziałek");
            model.addAttribute("czy",apteka.isPon());
            model.addAttribute("start",apteka.getPonStart());
            model.addAttribute("stop",apteka.getPonStop());
        } else if (liczba == 2) {
            model.addAttribute("dzien", "wtorek");
            model.addAttribute("czy",apteka.isWt());
            model.addAttribute("start",apteka.getWtStart());
            model.addAttribute("stop",apteka.getWtStop());
        } else if (liczba == 3) {
            model.addAttribute("dzien", "środa");
            model.addAttribute("czy",apteka.isSr());
            model.addAttribute("start",apteka.getSrStart());
            model.addAttribute("stop",apteka.getSrStop());
        } else if (liczba == 4) {
            model.addAttribute("dzien", "czwartek");
            model.addAttribute("czy",apteka.isCzw());
            model.addAttribute("start",apteka.getCzwStart());
            model.addAttribute("stop",apteka.getCzwStop());
        } else if (liczba == 5) {
            model.addAttribute("dzien", "piątek");
            model.addAttribute("czy",apteka.isPt());
            model.addAttribute("start",apteka.getPtStart());
            model.addAttribute("stop",apteka.getPtStop());
        } else if (liczba == 6) {
            model.addAttribute("dzien", "sobota");
            model.addAttribute("czy",apteka.isSb());
            model.addAttribute("start",apteka.getSbStart());
            model.addAttribute("stop",apteka.getSbStop());
        } else if (liczba == 7) {
            model.addAttribute("dzien", "niedziela");
            model.addAttribute("czy",apteka.isNdz());
            model.addAttribute("start",apteka.getNdzStart());
            model.addAttribute("stop",apteka.getNdzStop());
        }
        model.addAttribute("liczba",liczba);

        return "edytujGodzinyApteka";
    }

    @RequestMapping("/strefaAptekarza/edytowanoGodzinyApteka")
    public String edytowanoGodzinyApteka(Authentication authentication,
                                         @RequestParam int dzien,
                                         @RequestParam LocalTime start,
                                         @RequestParam LocalTime stop,
                                         @RequestParam(name = "czy", defaultValue = "false") boolean czy){
        String username= authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        if (stop.isBefore(start)) {
            return "zleGodzinyApteka";
        }
        serwisApteka.zmienGodziny(apteka,start,stop,dzien,czy);

        return "redirect:/Neuca/strefaAptekarza/godzinyApteka";
    }


    @RequestMapping("/strefaAptekarza/zleGodzinyApteka")
    public String zleGodzinyApteka(){
        return "zleGodzinyApteka";
    }



    @RequestMapping("/strefaAptekarza/edytujApteke")
    public String edytujApteke(Model model, Authentication authentication){
        String username= authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();

        List<Wojewodztwo> wojewodztwa= new ArrayList<>(Arrays.asList(Wojewodztwo.values()));
        wojewodztwa.remove(Wojewodztwo.ALL);
        model.addAttribute("wojewodztwa",wojewodztwa);

        model.addAttribute("apteka",apteka);
        return "edytujApteke";
    }

    @RequestMapping("/strefaAptekarza/edytowanoApteke")
    public String edytowanoApteke(Authentication authentication,
                                  Apteka aptekaNowa){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        serwisApteka.editApteka(apteka,aptekaNowa);
        return "edytowanoApteke";
    }


}
