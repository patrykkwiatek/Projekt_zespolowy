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
import java.util.Optional;

@RequestMapping("/Neuca")
@Controller
public class KontrolerApteka {
    SerwisMyUser serwisMyUser;
    SerwisApteka serwisApteka;
    SerwisPracownik serwisPracownik;
    SerwisLekStanApteka serwisLekStanApteka;

    public KontrolerApteka(SerwisLekStanApteka serwisLekStanApteka, SerwisPracownik serwisPracownik, SerwisMyUser serwisMyUser, SerwisApteka serwisApteka) {
        this.serwisLekStanApteka=serwisLekStanApteka;
        this.serwisMyUser = serwisMyUser;
        this.serwisApteka=serwisApteka;
        this.serwisPracownik=serwisPracownik;
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



    @RequestMapping("/strefaAptekarza/lekiDodawanieNaStan")
    public String lekiDodawanieNaStan(Model model){
        List<Lek> leki=serwisPracownik.getALL();
        model.addAttribute("leki",leki);


        return "lekiDodawanieNaStan";
    }


    @RequestMapping("/strefaAptekarza/lekiIleSztuk")
    public String lekiIleSztuk(@RequestParam Long id,
                                Model model){
        Lek lek=serwisPracownik.findbyID(id);
        model.addAttribute("lek", lek);
        return "lekiIleSztuk";
    }


    @RequestMapping("/strefaAptekarza/dodanoLekApteka")
    public String dodanoLekApteka(Authentication authentication, Model model,
                                  @RequestParam Long id, @RequestParam int sztuki){
        String username= authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();

        Lek lek=serwisPracownik.findbyID(id);
        serwisLekStanApteka.dodajDoApteki(apteka,sztuki, lek);


        return "dodanoLekApteka";
    }



    @RequestMapping("/strefaAptekarza/lekiNaStanie")
    public String lekiNaStanie(Model model, Authentication authentication){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka = myUser.getApteka();
        List<LekStanApteka> leki=serwisLekStanApteka.zwrocStan(apteka);
        model.addAttribute("leki",leki);
        return "lekiNaStanie";
    }


    @RequestMapping("/strefaAptekarza/lekNaStanieSzczegoly")
    public String lekNaStanieSzczegoly(Model model, @RequestParam Long id){
        Lek lek=serwisPracownik.findbyID(id);
        model.addAttribute("lek",lek);
        return "lekNaStanieSzczegoly";
    }

    @RequestMapping("/strefaAptekarza/dodawanieLekiSzczegoly")
    public String dodawanieLekiSzczegoly(Model model, @RequestParam Long id){
        Lek lek=serwisPracownik.findbyID(id);
        model.addAttribute("lek",lek);
        return "dodawanieLekiSzczegoly";
    }


    @RequestMapping("/strefaAptekarza/lekNaStanieEdytuj")
    public String lekNaStanieEdytuj(@RequestParam Long id, Model model){
        LekStanApteka lekStanApteka=serwisLekStanApteka.zwrocPoId(id);
        Lek lek=lekStanApteka.getLek();
        int sztuki=lekStanApteka.getSztuk();
        model.addAttribute("idLekS",lekStanApteka.getId());
        model.addAttribute("sztuk",sztuki);
        model.addAttribute("lek",lek);
        return "lekNaStanieEdytuj";
    }


    @RequestMapping("/strefaAptekarza/edytowanoLekApteka")
    public String edytowanoLekApteka(@RequestParam  Long id,
                                     @RequestParam int sztuki,
                                     Authentication authentication){
        String username=authentication.getName();
        MyUser myUser =serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        Lek lek=serwisPracownik.findbyID(id);
        LekStanApteka lekStanApteka=serwisLekStanApteka.zwrocPoAptekaILek(apteka,lek);
        serwisLekStanApteka.zapiszStan(lekStanApteka,sztuki);
        return "edytowanoLekApteka";
    }

    @RequestMapping("/strefaAptekarza/usunLekApteka")
    public String usunLekApteka(@RequestParam Long id, Model model){
        LekStanApteka lekStanApteka=serwisLekStanApteka.zwrocPoId(id);
        model.addAttribute("lek",lekStanApteka);
        return "usunLekApteka";
    }

    @RequestMapping("/strefaAptekarza/usunLekAptekaPotwierzdz")
    public String usunLekAptekaPotwierzdz(@RequestParam Long id){
        LekStanApteka lekStanApteka=serwisLekStanApteka.zwrocPoId(id);
        serwisLekStanApteka.usunStan(lekStanApteka);
        return "usunLekAptekaPotwierzdz";
    }





}
