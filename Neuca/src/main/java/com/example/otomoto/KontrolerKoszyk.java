package com.example.otomoto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RequestMapping("/Neuca")
@Controller
public class KontrolerKoszyk {
    private final SerwisPracownik sP;
    private final SerwisKlient sK;
    private final SerwisKoszyk sKoszyk;
    private final SerwisMyUser serwisMyUser;

    public KontrolerKoszyk(SerwisPracownik sP, SerwisKlient sK, SerwisKoszyk sKoszyk, SerwisMyUser serwisMyUser) {
        this.sP = sP;
        this.sK = sK;
        this.sKoszyk=sKoszyk;
        this.serwisMyUser=serwisMyUser;
    }


    @RequestMapping("/dodanoKoszyk")
    public String dodanoKoszyk(@RequestParam("lekId") Long lekId,
                               @RequestParam("ilosc") int ilosc,
                               Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "Ids") String sort,
                               @RequestParam(defaultValue = "") String wzorzec,
                               @RequestParam(defaultValue = "0") int minPrize,
                               @RequestParam(defaultValue = "1000") int maxPrize,
                               @RequestParam(required = false) MarkaLeku markaLeku,
                               @RequestParam(required = false) RodzajLeku rodzajLeku,
                               Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "error";
        }
        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);

        Lek lek = sK.zwrocLek(lekId);
        sKoszyk.dodajDoKoszyka(user,lek, ilosc);

        model.addAttribute("pageN",page);
        model.addAttribute("sizeN",size);
        model.addAttribute("sortN",sort);
        model.addAttribute("wzorzecN",wzorzec);
        model.addAttribute("minPrizeN",minPrize);
        model.addAttribute("maxPrizeN",maxPrize);
        model.addAttribute("markaLekuN",markaLeku);
        model.addAttribute("rodzajLekuN",rodzajLeku);
        model.addAttribute("lek", lek);
        model.addAttribute("ile", ilosc);
        return "dodanoKoszyk";
    }

    @RequestMapping("/koszyk")
    public String pokazKoszyk(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "error";
        }
        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);
        boolean czyPusty = sKoszyk.czyKoszykJestPusty(user);
        model.addAttribute("czyPusty", czyPusty);

        Zamowienie koszyk = sKoszyk.getAktywnyKoszyk(user);
        long sumaKoszyka = 0; // Zmienna na łączną cenę w groszach

        // Dodanie cen do każdego produktu w koszyku
        for (ProduktKoszyk produkt : koszyk.getProduktKoszyk()) {
            long cenaJednostkowaGrosze = produkt.getLek().getPriceGR(); // Cena jednostkowa w groszach
            long cenaCalosciowaGrosze = cenaJednostkowaGrosze * produkt.getIlosc(); // Cena całkowita w groszach

            produkt.setCenaJednostkowa(String.format("%.2f", cenaJednostkowaGrosze / 100.0)); // Przekształcenie na złote
            produkt.setCenaCalosciowa(String.format("%.2f", cenaCalosciowaGrosze / 100.0)); // Przekształcenie na złote

            sumaKoszyka += cenaCalosciowaGrosze; // Dodanie ceny całkowitej do sumy koszyka
        }

        model.addAttribute("koszyk", koszyk.getProduktKoszyk());
        model.addAttribute("sumaKoszyka", sumaKoszyka); // Dodanie łącznej ceny w groszach do modelu

        return "koszyk";
    }



    @RequestMapping("usunZKoszyka")
    public String usunZKoszyka(@RequestParam Long produktKoszykId,
                               Authentication authentication,
                               Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "error";
        }


        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);
        sKoszyk.usunZKoszyka(user, produktKoszykId);
        return "redirect:/Neuca/koszyk";
    }

    @RequestMapping("/realizacja")
    public String realizacja(Authentication authentication, Model model ){
        if (authentication == null || !authentication.isAuthenticated()) {
            return "error";
        }
        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);
        model.addAttribute("dostawy", Dostawa.values());

        return "realizacja";
    }
    @RequestMapping("/podsumowanie")
    public String podsumowanie(Model model, Authentication authentication,
                               @RequestParam String imie,
                               @RequestParam String nazwisko,
                               @RequestParam String email,
                               @RequestParam int telefon,
                               @RequestParam String adres1,
                               @RequestParam String adres2,
                               @RequestParam Dostawa dostawa,
                               @RequestParam(required = false, defaultValue = "false") boolean czyFaktura,
                               @RequestParam(required = false) String nazwaFirmy,
                               @RequestParam(required = false) String adres1F,
                               @RequestParam(required = false) String adres2F,
                               @RequestParam(required = false) String NIP) {

        // Twoje już istniejące dane w modelu
        model.addAttribute("imie", imie);
        model.addAttribute("nazwisko", nazwisko);
        model.addAttribute("email", email);
        model.addAttribute("telefon", telefon);
        model.addAttribute("adres1", adres1);
        model.addAttribute("adres2", adres2);
        model.addAttribute("dostawa", dostawa);
        model.addAttribute("czyFaktura", czyFaktura);
        model.addAttribute("nazwaFirmy", nazwaFirmy);
        model.addAttribute("adres1F", adres1F);
        model.addAttribute("adres2F", adres2F);
        model.addAttribute("NIP", NIP);

        // Zakładając, że użytkownik jest zalogowany
        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);
        Zamowienie koszyk = sKoszyk.getAktywnyKoszyk(user);
        int sumaKoszyka=0;

        for (ProduktKoszyk produkt : koszyk.getProduktKoszyk()) {
            long cenaJednostkowaGrosze = produkt.getLek().getPriceGR();
            long cenaCalosciowaGrosze = cenaJednostkowaGrosze * produkt.getIlosc();

            produkt.setCenaJednostkowa(String.format("%.2f", cenaJednostkowaGrosze / 100.0));
            produkt.setCenaCalosciowa(String.format("%.2f", cenaCalosciowaGrosze / 100.0));
            sumaKoszyka += cenaCalosciowaGrosze; // Dodanie ceny całkowitej do sumy koszyka
        }

        model.addAttribute("imie", imie);
        model.addAttribute("nazwisko", nazwisko);
        model.addAttribute("email", email);
        model.addAttribute("telefon", telefon);
        model.addAttribute("adres1", adres1);
        model.addAttribute("adres2", adres2);
        model.addAttribute("dostawa", dostawa);
        model.addAttribute("czyFaktura", czyFaktura);
        model.addAttribute("nazwaFirmy", nazwaFirmy);
        model.addAttribute("adres1F", adres1F);
        model.addAttribute("adres2F", adres2F);
        model.addAttribute("NIP", NIP);


        model.addAttribute("koszyk", koszyk.getProduktKoszyk());
        model.addAttribute("sumaKoszyka", sumaKoszyka); //
        return "podsumowanie";
    }

    @RequestMapping("/zamowienieUkonczone")
    public String zamowienieUkonczone(Model model, Authentication authentication,
                                      @RequestParam String imie,
                                      @RequestParam String nazwisko,
                                      @RequestParam String email,
                                      @RequestParam int telefon,
                                      @RequestParam String adres1,
                                      @RequestParam String adres2,
                                      @RequestParam Dostawa dostawa,
                                      @RequestParam(required = false, defaultValue = "false") boolean czyFaktura,
                                      @RequestParam(required = false) String nazwaFirmy,
                                      @RequestParam(required = false) String adres1F,
                                      @RequestParam(required = false) String adres2F,
                                      @RequestParam(required = false) String NIP){
        String username = authentication.getName();
        MyUser user = serwisMyUser.zwrocUser(username);
        Zamowienie koszyk=sKoszyk.getAktywnyKoszyk(user);
        koszyk.setImie(imie);
        koszyk.setNazwisko(nazwisko);
        koszyk.setEmail(email);
        koszyk.setTelefon(telefon);
        koszyk.setAdres1(adres1);
        koszyk.setAdres2(adres2);
        koszyk.setDostawa(dostawa);

        LocalDateTime data= LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        koszyk.setDisplayDateZamowienia(data.format(formatter));
        String dataF=data.format(formatter);


        koszyk.setDataZamowienia(data);
        koszyk.setFaktura(new Faktura(czyFaktura,nazwaFirmy,adres1F,adres2F,NIP,data,dataF,koszyk));
        koszyk.setStatus(Status.OCZEKUJE);
        koszyk.setCzyZakonczone(true);


        int sumaKoszyka=0;
        for (ProduktKoszyk produkt : koszyk.getProduktKoszyk()) {
            long cenaJednostkowaGrosze = produkt.getLek().getPriceGR();
            long cenaCalosciowaGrosze = cenaJednostkowaGrosze * produkt.getIlosc();


            sumaKoszyka += cenaCalosciowaGrosze; // Dodanie ceny całkowitej do sumy koszyka
        }

        koszyk.setCalkowitaCena(String.format("%.2f", sumaKoszyka / 100.0));
        sKoszyk.noweZamowienie(koszyk,user);

        return "zamowienieUkonczone";
    }

}
