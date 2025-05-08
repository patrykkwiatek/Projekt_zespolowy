package com.example.otomoto;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequestMapping("/Neuca")
@Controller
public class KontrolerLekarz {
    SerwisLekarz serwisLekarz;
    SerwisMyUser serwisMyUser;
    SerwisGrafik serwisGrafik;
    SerwisWizyta serwisWizyta;


    KontrolerLekarz(SerwisLekarz serwisLekarz,SerwisMyUser serwisMyUser,SerwisGrafik serwisGrafik, SerwisWizyta serwisWizyta){
        this.serwisLekarz=serwisLekarz;
        this.serwisMyUser=serwisMyUser;
        this.serwisGrafik=serwisGrafik;
        this.serwisWizyta=serwisWizyta;
    }


    @RequestMapping("/strefaLekarza")
    public String strefaLekarza(Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/Neuca/login";
        }
        String username = authentication.getName();
        MyUser myUser = serwisMyUser.zwrocUser(username);
        Lekarz lekarz = myUser.getLekarz();
        if(lekarz != null && lekarz.isPotwierdzenie()==false){
            return "redirect:/Neuca/strefaLekarza/potwierdzLekarza";
        }

        if (lekarz == null) {
            return "redirect:/Neuca/strefaLekarza/UtworzGabinet";
        }
        return "strefaLekarza";
    }

    @RequestMapping("/strefaLekarza/UtworzGabinet")
    public String utworzGabinet(Lekarz lekarz, Model model){
        List<LekarzSpec> spece=Arrays.asList(LekarzSpec.values());
        model.addAttribute("spece",spece);
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


    @RequestMapping("/strefaLekarza/GodzinyPracy")
    public String GodzinyPracy(Authentication authentication, Model model){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Lekarz lekarz=myUser.getLekarz();
        GrafikLekarz pon = serwisGrafik.znajdzGrafik(lekarz, DayOfWeek.MONDAY);
        GrafikLekarz wt = serwisGrafik.znajdzGrafik(lekarz, DayOfWeek.TUESDAY);
        GrafikLekarz sr = serwisGrafik.znajdzGrafik(lekarz, DayOfWeek.WEDNESDAY);
        GrafikLekarz czw = serwisGrafik.znajdzGrafik(lekarz, DayOfWeek.THURSDAY);
        GrafikLekarz pt = serwisGrafik.znajdzGrafik(lekarz, DayOfWeek.FRIDAY);
        GrafikLekarz sob = serwisGrafik.znajdzGrafik(lekarz, DayOfWeek.SATURDAY);
        GrafikLekarz nd = serwisGrafik.znajdzGrafik(lekarz, DayOfWeek.SUNDAY);
        model.addAttribute("pon", pon);
        model.addAttribute("wt", wt);
        model.addAttribute("sr", sr);
        model.addAttribute("czw", czw);
        model.addAttribute("pt", pt);
        model.addAttribute("sob", sob);
        model.addAttribute("nd", nd);

        return "GodzinyPracy";
    }

    @RequestMapping("/strefaLekarza/edytujGodzinyPracy")
    public String edytujGodzinyPracy(@RequestParam int dzien,
                                     Authentication authentication,
                                     Model model){
        String username= authentication.getName();
        MyUser myuser=serwisMyUser.zwrocUser(username);
        Lekarz lekarz=myuser.getLekarz();
        GrafikLekarz grafik=serwisGrafik.znajdzGrafik(lekarz,serwisGrafik.zwrocDzien(dzien));
        LocalTime start=grafik.getGodzinaStart();
        LocalTime koniec=grafik.getGodzinaKoniec();
        boolean zamkniete= grafik.isZamkniete();


        model.addAttribute("intdzien", dzien);
        model.addAttribute("dzien",serwisGrafik.zwrocDzienString(dzien));
        model.addAttribute("start",start);
        model.addAttribute("koniec",koniec);
        model.addAttribute("zamkniete",zamkniete);
        return "edytujGodzinyPracy";
    }


    @RequestMapping("/strefaLekarza/zapiszGodziny")
    public String zapiszGodziny(Authentication authentication,
                                @RequestParam int dzien,
                                @RequestParam LocalTime start,
                                @RequestParam LocalTime koniec,
                                boolean zamkniete){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Lekarz lekarz= myUser.getLekarz();
        serwisGrafik.zmienGrafik(lekarz,dzien,start,koniec,zamkniete);
        return "zapiszGodziny";

    }

    @RequestMapping("/strefaLekarza/edytujWizyty")
    public String edytujWizyty(@RequestParam int dzien,
                               Authentication authentication,
                               Model model){
        String username=authentication.getName();
        MyUser myuser=serwisMyUser.zwrocUser(username);
        Lekarz lekarz=myuser.getLekarz();
        GrafikLekarz grafikLekarz =serwisGrafik.znajdzGrafik(lekarz,serwisGrafik.zwrocDzien(dzien));
        List<Integer> lista=serwisGrafik.zwrocGodziny(grafikLekarz);
        model.addAttribute("lista", lista);
        return "edytujWizyty";
    }

    @RequestMapping("/strefaLekarza/zapiszWizyty")
    public String zapiszWizyty(@RequestParam int dzien,
                               @RequestParam List<Integer> godziny,
                               Authentication authentication,
                               Model model){
        String username=authentication.getName();
        MyUser myUser =serwisMyUser.zwrocUser(username);
        Lekarz lekarz= myUser.getLekarz();
        GrafikLekarz grafikLekarz=serwisGrafik.znajdzGrafik(lekarz,serwisGrafik.zwrocDzien(dzien));
        grafikLekarz.setGodziny(godziny);
        serwisGrafik.zapiszGrafik(grafikLekarz);
        model.addAttribute("godziny", grafikLekarz.getGodziny());
        return "zapiszWizyty";
    }

    @RequestMapping("/lekarze")
    public String lekarze(@RequestParam int page,
                          @RequestParam int size,
                          @RequestParam(defaultValue = "WSZYSTKIE") LekarzSpec spec,
                          @RequestParam String miasto,
                          Model model){
        Page<Lekarz> lekarze=serwisLekarz.getALL(page,size);
        List<LekarzSpec> spece=Arrays.asList(LekarzSpec.values());
        model.addAttribute("lekarze", lekarze);
        model.addAttribute("spece", spece);

        return "lekarze";
    }


    @RequestMapping("/strefaLekarza/edytujGabinet")
    public String edytujGabinet(Authentication authentication, Model model){
        String username= authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Lekarz lekarz=myUser.getLekarz();
        List<LekarzSpec> spece=Arrays.asList(LekarzSpec.values());

        model.addAttribute("spece",spece);
        model.addAttribute("lekarz",lekarz);
        return "edytujGabinet";
    }


    @RequestMapping("/strefaLekarza/edytowanoGabinet")
    public String edytowanoGabinet(Authentication authentication, Lekarz lekarzN){
        String username= authentication.getName();
        MyUser myUser =serwisMyUser.zwrocUser(username);
        Lekarz lekarz=myUser.getLekarz();
        serwisLekarz.editLekarz(lekarz,lekarzN);
        return "edytowanoGabinet";
    }


    @RequestMapping("/strefaLekarza/przegladajwizyty")
    public String przegladajwizyty(Authentication authentication, Model model,
                                   @RequestParam(defaultValue ="0") int page,
                                   @RequestParam(defaultValue = "id") String sort,
                                   @RequestParam String status
                                   ){
        String username= authentication.getName();;
        MyUser myUser =serwisMyUser.zwrocUser(username);
        Lekarz lekarz =myUser.getLekarz();
        Page<Wizyta> wizyty;
        if("all".equals(status)){
            wizyty= serwisLekarz.zwrocWizyty(page,sort,lekarz);
        }else{
            StatusWizyty statusWizyty=StatusWizyty.valueOf(status);
            wizyty=serwisLekarz.zwrocWizytyStatus(page,sort,lekarz,statusWizyty);
        }

        List<StatusWizyty> statusy=Arrays.asList(StatusWizyty.values());
        model.addAttribute("statusN",status);
        model.addAttribute("statusy", statusy);
        model.addAttribute("wizyty",wizyty);
        return "przegladajwizyty";
    }


    @RequestMapping("/strefaLekarza/wizyta")
    public String wizyta(Model model,
                         @RequestParam Long id){
        Wizyta wizyta=serwisWizyta.zwrocWizytaID(id);
        MyUser myUser=wizyta.getMyUser();
        String plec;
        if(myUser.isPlec()){
            plec="meżczyzna";
        }else{
            plec="kobieta";
        }
        Boolean czyAnulowana;
        if(wizyta.getStatusWizyty()!=StatusWizyty.ANULOWANA && wizyta.getStatusWizyty() !=StatusWizyty.ZREALIZOWANA){
            czyAnulowana=true;
        }else{
            czyAnulowana=false;
        }
        model.addAttribute("czyAnulowana",czyAnulowana);


        Boolean czyPotw;
        if(wizyta.getStatusWizyty()!=StatusWizyty.ZAREZERWOWANAPOTW && wizyta.getStatusWizyty()!=StatusWizyty.ZREALIZOWANA &&wizyta.getStatusWizyty()!= StatusWizyty.ANULOWANA){
            czyPotw=true;
        }else{
            czyPotw=false;
        }
        model.addAttribute("czyPotw",czyPotw);


        Boolean czyZreal;
        if(wizyta.getStatusWizyty()!=StatusWizyty.ZREALIZOWANA && wizyta.getStatusWizyty()!=StatusWizyty.ANULOWANA &&wizyta.getStatusWizyty()!=StatusWizyty.ZAREZERWOWANABRAKPOTW){
            czyZreal=true;
        }else{
            czyZreal=false;
        }
        model.addAttribute("czyZreal",czyZreal);
        String zalecenia=wizyta.getZalecenia();
        if(zalecenia==null){
            zalecenia=" ";
        }
        model.addAttribute("zalecenia",zalecenia);
        model.addAttribute("status",wizyta.getStatusWizyty());
        model.addAttribute("data",wizyta.getData());
        model.addAttribute("opis",wizyta.getOpis());
        model.addAttribute("plec",plec);
        model.addAttribute("wizytaId",wizyta.getId());
        model.addAttribute("imie",myUser.getImie());
        model.addAttribute("nazwisko",myUser.getNazwisko());
        model.addAttribute("login",myUser.getUsername());
        model.addAttribute("email",myUser.getEmail());
        model.addAttribute("pacjentId",myUser.getId());
        return "wizyta";
    }


    @RequestMapping("/strefaLekarza/anuluj")
    public String anuluj(@RequestParam Long id){
        serwisWizyta.zmienStatus(id,StatusWizyty.ANULOWANA);
        return "redirect:/Neuca/strefaLekarza/wizyta?id=" + id;
    }
    @RequestMapping("/strefaLekarza/potwierdz")
    public String potwierdz(@RequestParam Long id){
        serwisWizyta.zmienStatus(id, StatusWizyty.ZAREZERWOWANAPOTW);
        return "redirect:/Neuca/strefaLekarza/wizyta?id=" + id;
    }

    @RequestMapping("/strefaLekarza/realizuj")
    public String realizuj(@RequestParam Long id){
        serwisWizyta.zmienStatus(id, StatusWizyty.ZREALIZOWANA);
        return "redirect:/Neuca/strefaLekarza/wizyta?id=" + id;
    }


    @RequestMapping("/strefaLekarza/wizytaZalecenia")
    public String wizytaZalecenia(Model model, @RequestParam  Long wizytaID){
        Wizyta wizyta=new Wizyta();
        wizyta=serwisWizyta.zwrocWizytaID(wizytaID);
        model.addAttribute("zalecenia",wizyta.getZalecenia());
        model.addAttribute("id", wizyta.getId());
        return "wizytaZalecenia";

    }
    @RequestMapping("/strefaLekarza/wizytaZaleceniaZmien")
    public String wizytaZaleceniaZmien(@RequestParam String zalecenia,
                                       @RequestParam Long id){
        Wizyta wizyta=serwisWizyta.zwrocWizytaID(id);
        wizyta.setZalecenia(zalecenia);
        serwisWizyta.zapiszWizyte(wizyta);
        return "redirect:/Neuca/strefaLekarza/wizyta?id=" + id;
    }




}
