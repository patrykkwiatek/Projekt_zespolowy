package com.example.otomoto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Neuca")
public class KontrolerKlient {
    private final SerwisPracownik sP;
    private final SerwisKlient sK;
    private final SerwisApteka serwisApteka;
    private final SerwisLekStanApteka serwisLekStanApteka;
    private final SerwisRezerwacjaLeku serwisRezerwacjaLeku;
    private final SerwisMyUser serwisMyUser;
    private final SerwisWizyta serwisWizyta;

    public KontrolerKlient(SerwisWizyta serwisWizyta, SerwisMyUser serwisMyUser, SerwisRezerwacjaLeku serwisRezerwacjaLeku, SerwisApteka serwisApteka, SerwisLekStanApteka serwisLekStanApteka, SerwisPracownik sP, SerwisKlient sK) {
        this.sP = sP;
        this.sK = sK;
        this.serwisLekStanApteka=serwisLekStanApteka;
        this.serwisApteka=serwisApteka;
        this.serwisRezerwacjaLeku=serwisRezerwacjaLeku;
        this.serwisMyUser =serwisMyUser;
        this.serwisWizyta=serwisWizyta;
    }

    @RequestMapping("/startK/listaK")
    public String lista(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "Ids") String sort,
                        @RequestParam(defaultValue = "") String wzorzec,
                        Model model) {

        /*List<Lek> filteredContent = wynik.getContent()
                .stream()
                .filter(lek -> lek.isOferta())
                .toList();

        Page<Lek> filteredWynik = new PageImpl<>
                (filteredContent, wynik.getPageable(), filteredContent.size());

        model.addAttribute("lista", filteredWynik);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);
        model.addAttribute("numbers", sP.createPageNumbers(page, filteredWynik.getTotalPages()));
        model.addAttribute("total", filteredWynik.getTotalPages());
        model.addAttribute("pageA", page + 1);
        model.addAttribute("wzorzec", wzorzec);*/
        return "listaK";
    }

    @RequestMapping("/startK/DodajDoKoszyka/{id}")
    public String DodajDoKoszyka(@PathVariable Long id){

        return "DodajDoKoszyka";
    }




    @RequestMapping("/pill")
    public String pill(){
        return "pill";
    }

    @RequestMapping("/buy")
    public String buy(Authentication authentication,Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "Ids") String sort,
                       @RequestParam(defaultValue = "") String wzorzec,
                       @RequestParam(defaultValue = "0") int minPrize,
                       @RequestParam(defaultValue = "1000") int maxPrize,
                       @RequestParam(required = false) MarkaLeku markaLeku,
                       @RequestParam(required = false) RodzajLeku rodzajLeku){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        if (markaLeku == null) {
            markaLeku = MarkaLeku.WSZYSTKIE;
        }
        if (rodzajLeku == null) {
            rodzajLeku = RodzajLeku.WSZYSTKIE;
        }
        Page<Lek> wyniki = sP.getAllDtoLeki(page, size, sort, wzorzec, minPrize, maxPrize, markaLeku, rodzajLeku,true);
        List<RodzajLeku> rodzaje = Arrays.asList(RodzajLeku.values());
        List<MarkaLeku> marki = Arrays.asList(MarkaLeku.values());

        if(minPrize!=0){
            model.addAttribute("minPrize",minPrize);
        }
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
        model.addAttribute("log", isLogged);
        model.addAttribute("size",size);
        return "buy";
    }



    @RequestMapping("/rezerwujLek")
    public String rezerwujLek(Model model,
                           @RequestParam Long id,
                           @RequestParam(defaultValue = "ALL") Wojewodztwo wojewodztwo){
        Lek lek=sP.findbyID(id);
        List<LekStanApteka> apteki=serwisLekStanApteka.zwrocTamGdzieLek(lek);
        if(wojewodztwo!=Wojewodztwo.ALL){
            Iterator<LekStanApteka> iterator = apteki.iterator();
            while (iterator.hasNext()) {
                LekStanApteka lekStan = iterator.next();
                Wojewodztwo w = lekStan.getApteka().getWojewodztwo();
                if (w != wojewodztwo) {
                    iterator.remove();
                }
            }
        }

        List<Wojewodztwo> wojewodztwa= new ArrayList<>(Arrays.asList(Wojewodztwo.values()));
        model.addAttribute("wojewodztwa",wojewodztwa);
        model.addAttribute("wojewodztwo",wojewodztwo);
        model.addAttribute("lek",lek);
        model.addAttribute("lekiStan",apteki);
        model.addAttribute("id",id);

        return "rezerwujLek";
    }


    @RequestMapping("/rezerwacjaZlozono")
    public String rezerwacjaZlozono(Authentication authentication, Model model,
                                      @RequestParam Long idApteki,@RequestParam Long idLeku, @RequestParam int ilosc,
                                      @RequestParam String imie, @RequestParam String nazwisko){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        Lek lek=sP.findbyID(idLeku);
        Apteka apteka=serwisApteka.znajdzApteka(idApteki);
        serwisRezerwacjaLeku.dodajRezerwacje(myUser,apteka,lek,ilosc,imie,nazwisko);
        model.addAttribute("apteka",apteka);
        model.addAttribute("lek",lek);
        model.addAttribute("ilosc",ilosc);
        return "rezerwacjaZlozono";
    }


    @RequestMapping("/edycjaPacjent")
    public String edycjaPacjent(Authentication authentication, Model model){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        model.addAttribute("imie",myUser.getImie());
        model.addAttribute("nazwisko",myUser.getNazwisko());
        model.addAttribute("telefon",myUser.getPhone());
        return "edycjaPacjent";
    }


    @RequestMapping("/edytowanoPacjent")
    public String edytowanoPacjent(@RequestParam String imie,
                                   @RequestParam String nazwisko,
                                   @RequestParam String telefon,
                                   Authentication authentication){
        int telefonINT=Integer.parseInt(telefon);
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        myUser.setImie(imie);
        myUser.setNazwisko(nazwisko);
        myUser.setPhone(telefonINT);
        serwisMyUser.zmienPacjent(myUser);
        return "redirect:/Neuca/mojProfilPacjent";
    }

    @RequestMapping("/mojeWizyty")
    public String mojeWizyty(Authentication authentication, Model model){
        String username=authentication.getName();
        MyUser myUser=serwisMyUser.zwrocUser(username);
        List<Wizyta> wizyty=serwisWizyta.zwrocMojeWizyty(myUser);
        model.addAttribute("wizyty",wizyty);
        return "mojeWizyty";
    }

    @RequestMapping("/szczegolyWizyta")
    public String szczegolyWizyta(@RequestParam Long id, Model model){
        Wizyta wizyta=serwisWizyta.zwrocWizytaID(id);
        model.addAttribute("wizyta",wizyta);


        return "szczegolyWizyta";
    }



    @RequestMapping("/czyAnulujWizyte")
    public String czyAnulujWizyte(@RequestParam Long id, Model model){
        Wizyta wizyta=serwisWizyta.zwrocWizytaID(id);
        model.addAttribute("wizyta",wizyta);
        return "czyAnulujWizyte";
    }

    @RequestMapping("/anulujWizyte")
    public String anulujWizyte(@RequestParam Long wizytaId){
        Wizyta w =serwisWizyta.zwrocWizytaID(wizytaId);
        serwisWizyta.zmienStatus(w,StatusWizyty.ANULOWANA);
        return "redirect:/Neuca/mojeWizyty";
    }
}
