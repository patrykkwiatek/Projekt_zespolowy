package com.example.otomoto;


import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.data.domain.Page;
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
    SerwisRezerwacjaLeku serwisRezerwacjaLeku;
    SerwisZamowienieApteka serwisZamowienieApteka;
    SerwisProduktKoszykApteka serwisProduktKoszykApteka;

    public KontrolerApteka(SerwisProduktKoszykApteka serwisProduktKoszykApteka, SerwisZamowienieApteka serwisZamowienieApteka, SerwisRezerwacjaLeku serwisRezerwacjaLeku, SerwisLekStanApteka serwisLekStanApteka, SerwisPracownik serwisPracownik, SerwisMyUser serwisMyUser, SerwisApteka serwisApteka) {
        this.serwisLekStanApteka=serwisLekStanApteka;
        this.serwisMyUser = serwisMyUser;
        this.serwisApteka=serwisApteka;
        this.serwisPracownik=serwisPracownik;
        this.serwisProduktKoszykApteka=serwisProduktKoszykApteka;
        this.serwisZamowienieApteka=serwisZamowienieApteka;
        this.serwisRezerwacjaLeku=serwisRezerwacjaLeku;
    }

    @RequestMapping("/strefaAptekarza")
    public String strefaAptekarza(Authentication authentication){
        return "strefaAptekarza";
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
        if (stop.isBefore(start) || stop.equals(start)) {
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
        return "redirect:/Neuca/strefaAptekarza";
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


    @RequestMapping("/strefaAptekarza/zarzadzajApteka")
    public String zarzadzajApteka(Model model, Authentication authentication){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        LocalDate dzisiaj=LocalDate.now();
        dzisiaj=dzisiaj.plusDays(1);
        List<RezerwacjaLeku> rezerwacjaLeku=serwisRezerwacjaLeku.zwrocRezerwacje(dzisiaj.plusDays(5),apteka);
        model.addAttribute("rezerwacje", rezerwacjaLeku);

        List<LekStanApteka> naStanie=serwisLekStanApteka.zwrocStan(apteka);
        model.addAttribute("naStanie",naStanie);
        return "zarzadzajApteka";
    }

    @RequestMapping("/strefaAptekarza/rezerwacjaSzczegoly")
    public String rezerwacjaSzczegoly(Model model, @RequestParam Long id){
        RezerwacjaLeku rezerwacjaLeku=serwisRezerwacjaLeku.zwrocPoId(id);
        model.addAttribute("rezerwacja",rezerwacjaLeku);
        StatusRezerwacji statusRezerwacji=rezerwacjaLeku.getStatusRezerwacji();
        boolean czyAnuluj;
        if (statusRezerwacji==StatusRezerwacji.W_TRAKCIE){
            czyAnuluj=true;
        }else{
            czyAnuluj=false;
        }
        model.addAttribute("czyAnuluj",czyAnuluj);

        boolean czyWydane;
        if(statusRezerwacji==StatusRezerwacji.WYDANO){
            czyWydane=false;
        }else{
            czyWydane=true;
        }
        model.addAttribute("czyWydano",czyWydane);

        return "rezerwacjaSzczegoly";
    }



    @RequestMapping("/strefaAptekarza/zarzadzajApekaLekSzczegoly")
    public String zarzadzajApekaLekSzczegoly(Model model, @RequestParam Long id){
        Lek lek=serwisPracownik.findbyID(id);
        model.addAttribute("lek",lek);
        return "zarzadzajApekaLekSzczegoly";
    }

    @RequestMapping("/strefaAptekarza/zmienStanRezerwacji")
    public String zmienStanRezerwacji(@RequestParam int ile,
                                      @RequestParam Long id){
        RezerwacjaLeku rezerwacjaLeku=serwisRezerwacjaLeku.zwrocPoId(id);
        if(ile==1){
            serwisRezerwacjaLeku.zmienStatus(StatusRezerwacji.WYDANO,rezerwacjaLeku);
        }else{
            serwisRezerwacjaLeku.zmienStatus(StatusRezerwacji.ANULOWANO,rezerwacjaLeku);
        }
        return "redirect:/Neuca/strefaAptekarza/rezerwacjaSzczegoly?id="+id;

    }


    @RequestMapping("/strefaAptekarza/buyApteka")
    public String buyApteka(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "Ids") String sort,
                            @RequestParam(defaultValue = "") String wzorzec,
                            @RequestParam(defaultValue = "0") int minPrize,
                            @RequestParam(defaultValue = "1000") int maxPrize,
                            @RequestParam(required = false) MarkaLeku markaLeku,
                            @RequestParam(required = false) RodzajLeku rodzajLeku){


        if (markaLeku == null) {
            markaLeku = MarkaLeku.WSZYSTKIE;
        }
        if (rodzajLeku == null) {
            rodzajLeku = RodzajLeku.WSZYSTKIE;
        }
        Page<Lek> wyniki = serwisPracownik.getAllDtoLeki(page, size, sort, wzorzec, minPrize, maxPrize, markaLeku, rodzajLeku,true);
        List<RodzajLeku> rodzaje = Arrays.asList(RodzajLeku.values());
        List<MarkaLeku> marki = Arrays.asList(MarkaLeku.values());
        if(minPrize!=0){
            model.addAttribute("minPrizeW",minPrize);
        }
        model.addAttribute("minPrize",minPrize);
        int total=wyniki.getTotalPages();
        boolean czy0;
        if(total==0){
            czy0=true;
        }else{
            czy0=false;
        }
        boolean czyPierwsza;
        if(page==0){
            czyPierwsza=true;
        }else{
            czyPierwsza=false;
        }

        boolean czyOstatnia;
        if(page+1==total){
            czyOstatnia=true;
        }else{
            czyOstatnia=false;
        }
        int nastepna=page+1;
        int poprzednia=page-1;

        model.addAttribute("total",total);
        model.addAttribute("total1",total-1);
        model.addAttribute("page",page+1);
        model.addAttribute("pageCof",page);

        model.addAttribute("pageN",page);
        model.addAttribute("poprzednia",poprzednia);
        model.addAttribute("nastepna",nastepna);
        model.addAttribute("czyPierwsza",czyPierwsza);
        model.addAttribute("czyOstatnia",czyOstatnia);
        model.addAttribute("czy0",czy0);
        model.addAttribute("wzorzec",wzorzec);
        model.addAttribute("maxPrize", maxPrize);
        model.addAttribute("sort", sort);
        model.addAttribute("rodzajLeku", rodzajLeku);
        model.addAttribute("markaLeku", markaLeku);
        model.addAttribute("rodzaje", rodzaje);
        model.addAttribute("marki", marki);
        model.addAttribute("wyniki", wyniki);
        model.addAttribute("size",size);
        return "buyApteka";
    }

    @RequestMapping("/strefaAptekarza/buyLekSzczegoly")
    public String buyLekSzczegoly(Model model, @RequestParam Long id,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "Ids") String sort,
                                  @RequestParam(defaultValue = "") String wzorzec,
                                  @RequestParam(defaultValue = "0") int minPrize,
                                  @RequestParam(defaultValue = "1000") int maxPrize,
                                  @RequestParam(required = false) MarkaLeku markaLeku,
                                  @RequestParam(required = false) RodzajLeku rodzajLeku){
        Lek lek=serwisPracownik.findbyID(id);
        model.addAttribute("lek",lek);


        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("sort",sort);
        model.addAttribute("wzorzec",wzorzec);
        model.addAttribute("minPrize",minPrize);
        model.addAttribute("maxPrize",maxPrize);
        model.addAttribute("markaLeku",markaLeku);
        model.addAttribute("rodzajLeku",rodzajLeku);


        return "buyLekSzczegoly";
    }

    @RequestMapping("/strefaAptekarza/dodanoDoKoszykaApteka")
    public String dodanoDoKoszykaApteka(@RequestParam Long lekId,
                                  @RequestParam int ilosc,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "Ids") String sort,
                                  @RequestParam(defaultValue = "") String wzorzec,
                                  @RequestParam(defaultValue = "0") int minPrize,
                                  @RequestParam(defaultValue = "1000") int maxPrize,
                                  @RequestParam(required = false) MarkaLeku markaLeku,
                                  @RequestParam(required = false) RodzajLeku rodzajLeku,
                                  Model model, Authentication authentication
                                  ){
        System.out.println(markaLeku);
        String username= authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        ZamowienieApteka zamowienieApteka=serwisZamowienieApteka.zwrocZamowienieAktualne(apteka);
        System.out.println(zamowienieApteka.getId());
        Lek lek=serwisPracownik.findbyID(lekId);
        model.addAttribute("lek",lek);
        model.addAttribute("ile",ilosc);

        int razem=ilosc*lek.getPriceGR();
        int zlote = razem / 100;
        int grosze = razem % 100;
        String cenaRazem=String.format("%d,%02d zł",zlote,grosze);
        model.addAttribute("cenaRazem",cenaRazem);
        serwisProduktKoszykApteka.dodaj(zamowienieApteka,lek,ilosc);
        model.addAttribute("page",page);
        model.addAttribute("size",size);
        model.addAttribute("sort",sort);
        model.addAttribute("wzorzec",wzorzec);
        model.addAttribute("minPrize",minPrize);
        model.addAttribute("maxPrize",maxPrize);
        model.addAttribute("markaLeku",markaLeku);
        model.addAttribute("rodzajLeku",rodzajLeku);
        return "dodanoDoKoszykaApteka";
    }


    @RequestMapping("/strefaAptekarza/koszykApteka")
    public String koszykApteka(Authentication authentication, Model model){
        String username= authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        ZamowienieApteka zamowienieApteka=serwisZamowienieApteka.zwrocZamowienieAktualne(apteka);
        List<ProduktKoszykApteka> produkty= zamowienieApteka.getProduktKoszykApteka();
        int suma=serwisZamowienieApteka.sumaKoszyk(zamowienieApteka);
        int zlote = suma / 100;
        int grosze = suma % 100;
        String cenaRazem=String.format("%d,%02d zł",zlote,grosze);
        model.addAttribute("cenaRazem",cenaRazem);
        model.addAttribute("produkty",produkty);
        model.addAttribute("czyPusty",produkty.isEmpty());
        return "koszykApteka";
    }

    @RequestMapping("/strefaAptekarza/usunZKoszyka")
    public String usunZKoszyka (@RequestParam Long id, Authentication authentication){
        String username= authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        Lek lek=serwisPracownik.findbyID(id);
        ZamowienieApteka zamowienieApteka=serwisZamowienieApteka.zwrocZamowienieAktualne(apteka);
        ProduktKoszykApteka p=serwisProduktKoszykApteka.zwrocProdukt(id);
        serwisProduktKoszykApteka.usun(p);
        return "redirect:/Neuca/strefaAptekarza/koszykApteka";
    }

    @RequestMapping("/strefaAptekarza/potwierdzAptekaZamowienie")
    public String potwierdzAptekaZamowienie(){
        return "potwierdzAptekaZamowienie";
    }
    @RequestMapping("/strefaAptekarza/zlozonoZamowieniaApteka")
    public String zlozonoZamowieniaApteka(Authentication authentication){
        String username= authentication.getName();
        MyUser m=serwisMyUser.zwrocUser(username);
        Apteka apteka=m.getApteka();
        ZamowienieApteka z=serwisZamowienieApteka.zwrocZamowienieAktualne(apteka);
        serwisZamowienieApteka.zamow(z);
        return "zlozonoZamowieniaApteka";
    }

    @RequestMapping("/strefaAptekarza/zamowieniaApteka")
    public String zamowieniaApteka(Authentication authentication, Model model){
        String username= authentication.getName();;
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Apteka apteka=myUser.getApteka();
        List<ZamowienieApteka> zamowienieApteka=serwisZamowienieApteka.zwrocZamowieniaApteka(apteka);
        model.addAttribute("zamowienia",zamowienieApteka);
        return "zamowieniaApteka";
    }


    @RequestMapping("/strefaAptekarza/szczegolyZamowieniaApteka")
    public String szczegolyZamowieniaApteka(Model model, @RequestParam Long id){
        ZamowienieApteka z=serwisZamowienieApteka.zwrocZamowieniePoId(id);
        model.addAttribute("z",z);
        StatusZamoweniaApteka s=z.getStatusZamoweniaApteka();
        boolean zal=z.isCzyZaladowane();
        if(s.equals(StatusZamoweniaApteka.ANULOWANE)){
            model.addAttribute("czyAnul",false);
        }else{
            model.addAttribute("czyAnul",true);
        }
        if(zal==true){
            model.addAttribute("czyZal",false);
        }else{
            model.addAttribute("czyZal",true);
        }
        return "szczegolyZamowieniaApteka";
    }



    @RequestMapping("/strefaAptekarza/zaladujZamowienieApteka")
    public String zaladujZamowienieApteka(@RequestParam Long id){
        ZamowienieApteka z=serwisZamowienieApteka.zwrocZamowieniePoId(id);
        serwisZamowienieApteka.zaladujZamowienie(z);
        serwisZamowienieApteka.zmienStatus(z,StatusZamoweniaApteka.ZAKONCZONE);
        return "redirect:/Neuca/strefaAptekarza/szczegolyZamowieniaApteka?id=" + id;
    }


    @RequestMapping("/strefaAptekarza/anulujZamowieniaApteka")
    public String anulujZamowieniaApteka(@RequestParam Long id){
        ZamowienieApteka z=serwisZamowienieApteka.zwrocZamowieniePoId(id);
        serwisZamowienieApteka.zmienStatus(z,StatusZamoweniaApteka.ANULOWANE);
        return "redirect:/Neuca/strefaAptekarza/szczegolyZamowieniaApteka?id=" + id;
    }











}
