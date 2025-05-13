package com.example.otomoto;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Neuca")
public class KontrolerPracownik {
    private final SerwisPracownik s;
    private final SerwisKoszyk serwisKoszyk;
    private final SerwisMyUser serwisMyUser;
    private final SerwisApteka serwisApteka;
    private final SerwisLekarz serwisLekarz;


    public KontrolerPracownik(SerwisLekarz serwisLekarz, SerwisPracownik s,SerwisKoszyk serwisKoszyk,SerwisApteka serwisApteka,SerwisMyUser serwisMyUser) {
        this.s = s;
        this.serwisKoszyk=serwisKoszyk;
        this.serwisMyUser=serwisMyUser;
        this.serwisApteka=serwisApteka;
        this.serwisLekarz=serwisLekarz;
    }

    @RequestMapping("/start")
    public String start(Authentication authentication, Model model){
        boolean isLogged;
        if (authentication != null && authentication.isAuthenticated()) {
            String role = authentication.getAuthorities().toString();
            role = role.replace("ROLE_", "");
            isLogged=true;
            model.addAttribute("log",isLogged);
        } else {
            isLogged=false;
            model.addAttribute("log",isLogged);
        }
        List<RodzajLeku> leki = Arrays.asList(RodzajLeku.values());
        List<LekarzSpec> lekarze = Arrays.asList(LekarzSpec.values());
        model.addAttribute("lekarze",lekarze);
        model.addAttribute("leki", leki);
        return "start";
    }





    @RequestMapping("/strefaPracownika/dodawanieLeku")
    public String dodawanieLeku(Lek lek,Model model){
        List<MarkaLeku> marki=Arrays.asList(MarkaLeku.values());
        List<RodzajLeku> rodzaje =Arrays.asList(RodzajLeku.values());
        model.addAttribute("marki", marki);
        model.addAttribute("rodzaje", rodzaje);
        return "dodawanieLeku";
    }



    @RequestMapping("/strefaPracownika/dodanoLek")
    public String dodano(Lek lek, @RequestParam("sciezkaPliku") MultipartFile plik, Model model) {
        if (!plik.isEmpty()) {
            try {

                // Ścieżka do katalogu 'uploads' w katalogu głównym projektu
                String folderPath = "C:/Users/User/Desktop/naGita/Neuca/uploads";
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs(); // Tworzymy folder, jeśli nie istnieje
                }

                // Unikalna nazwa pliku (żeby nie nadpisać)
                String fileName = UUID.randomUUID() + "_" + plik.getOriginalFilename();
                File targetFile = new File(folderPath + "/" + fileName);

                // Zapis pliku
                plik.transferTo(targetFile);

                // Ścieżka do obrazka widoczna w HTML - endpoint będzie potrzebny
                lek.setSciezka(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(lek.getSciezka());
        s.DodajLek(lek);
        model.addAttribute("lek", lek);
        return "dodanoLek";
    }


    @RequestMapping("/strefaPracownika/usunLek/{id}")
    public String usunLek(@PathVariable Long id, Model model){
        Lek lek=s.findbyID(id);
        model.addAttribute("lek",lek);
        return "usunLek";
    }

    @RequestMapping("/strefaPracownika/usunieto/{id}")
    public String usunieto(@PathVariable Long id){
        s.deleteLek(id);
        return "usunieto";
    }


    @RequestMapping("/strefaPracownika/edycjaLeku/{id}")
    public String edycjaLeku(@PathVariable Long id, Model model){
        List<MarkaLeku> marki=Arrays.asList(MarkaLeku.values());
        List<RodzajLeku> rodzaje=Arrays.asList(RodzajLeku.values());
        Lek lek =s.findbyID(id);
        model.addAttribute("marki",marki);
        model.addAttribute("rodzaje",rodzaje);
        model.addAttribute("lek",lek);
        return "edycjaLeku";
    }

    @RequestMapping("/strefaPracownika/edytowano/{id}")
    public String edytowano(@PathVariable Long id, @RequestParam("sciezkaPliku") MultipartFile plik, Lek lek, Model model) {
        if (!plik.isEmpty()) {
            try {
                // Ścieżka do katalogu 'uploads' w katalogu głównym projektu
                String folderPath = "C:/Users/User/Desktop/naGita/Neuca/uploads";
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs(); // Tworzymy folder, jeśli nie istnieje
                }

                // Unikalna nazwa pliku (żeby nie nadpisać)
                String fileName = UUID.randomUUID() + "_" + plik.getOriginalFilename();
                File targetFile = new File(folderPath + "/" + fileName);

                // Zapis pliku
                plik.transferTo(targetFile);

                // Ścieżka do obrazka widoczna w HTML - endpoint będzie potrzebny
                lek.setSciezka(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Lek updatedLek = s.updateLek(id, lek);
        model.addAttribute("lek", updatedLek);
        return "edytowano";
    }





    @RequestMapping("/strefaPracownika/przegladajLeki")
    public String przegladajLeki(@RequestParam(defaultValue = "0") int page,

                        @RequestParam(defaultValue = "") String wzorzec,
                        Model model) {
        Page<Lek> wynik = s.getAllDtoLeki(page,10,"Id",wzorzec,0,1000,MarkaLeku.WSZYSTKIE,null,false);

        boolean czyPoprzednia;
        if(page==0){
            czyPoprzednia=false;
        }else{
            czyPoprzednia=true;
        }
        model.addAttribute("czyPoprzednia",czyPoprzednia);

        boolean czyNastepna;
        if(page== wynik.getTotalPages()-1){
            czyNastepna=false;
        }else{
            czyNastepna=true;
        }
        model.addAttribute("czyNastepna",czyNastepna);

        model.addAttribute("lista", wynik);

        model.addAttribute("pop",page-1);
        if(wynik.getTotalElements()==0){
            model.addAttribute("nas",page);
        }else{
            model.addAttribute("nas",page+1);
        }
        model.addAttribute("all",wynik.getTotalPages());
        model.addAttribute("wzorzec",wzorzec);
        return "przegladajLeki";
    }
    @RequestMapping("/strefaPracownika")
    public String strefaPracownika(){
        return "strefaPracownika";
    }

    @RequestMapping("/strefaPracownika/realizujZamowienia")
    public String realizujZamowienia(Model model,
                                     @RequestParam(defaultValue = "ALL") Status status,
                                     @RequestParam(defaultValue = "0") int page){

        PageRequest pageable = PageRequest.of(page, 10);

        List<Status> statusy = Arrays.stream(Status.values())
                .filter(s -> s != Status.BRAK)
                .collect(Collectors.toList());
        model.addAttribute("statusy",statusy);
        Page<Zamowienie> zamowienia=s.pobierzListeZamowien(status,pageable);
        model.addAttribute("zamowienia",zamowienia);
        model.addAttribute("statusy", statusy);
        model.addAttribute("wybranyStatus", status);
        return "realizujZamowienia";
    }

    @RequestMapping("strefaPracownika/Zamowienie")
    public String Zamowienie(@RequestParam Long id, Model model){
        Zamowienie koszyk=s.zwrocZamowienie(id);
        int sumaKoszyka=0;

        for (ProduktKoszyk produkt : koszyk.getProduktKoszyk()) {
            long cenaJednostkowaGrosze = produkt.getLek().getPriceGR();
            long cenaCalosciowaGrosze = cenaJednostkowaGrosze * produkt.getIlosc();

            produkt.setCenaJednostkowa(String.format("%.2f", cenaJednostkowaGrosze / 100.0));
            produkt.setCenaCalosciowa(String.format("%.2f", cenaCalosciowaGrosze / 100.0));
            sumaKoszyka += cenaCalosciowaGrosze; // Dodanie ceny całkowitej do sumy koszyka
        }

        Faktura faktura=koszyk.getFaktura();
        model.addAttribute("koszyk", koszyk.getProduktKoszyk());
        model.addAttribute("sumaKoszyka", sumaKoszyka);


        model.addAttribute("id", koszyk.getId());
        model.addAttribute("imie", koszyk.getImie());
        model.addAttribute("nazwisko", koszyk.getNazwisko());
        model.addAttribute("email", koszyk.getEmail());
        model.addAttribute("telefon", koszyk.getTelefon());
        model.addAttribute("adres1", koszyk.getAdres1());
        model.addAttribute("adres2", koszyk.getAdres2());
        model.addAttribute("dostawa", koszyk.getDostawa());
        model.addAttribute("czyFaktura", faktura.isCzyFaktura());
        model.addAttribute("nazwaFirmy", faktura.getNazwaFirmy());
        model.addAttribute("adres1F", faktura.getAdres1());
        model.addAttribute("adres2F", faktura.getAdres2());
        model.addAttribute("NIP", faktura.getNip());
        model.addAttribute("status", koszyk.getStatus());

        model.addAttribute("id", koszyk.getId());
        return "Zamowienie";
    }

    @RequestMapping("/strefaPracownika/zmienStatus")
    public String zmienStatus(Authentication authentication, Model model,
                              @RequestParam Long id
                              ){
        Zamowienie zamowienie=s.zwrocZamowienie(id);
        s.zmienStatusZamowienia(zamowienie);

        return "redirect:/Neuca/strefaPracownika/Zamowienie?id=" + id;
    }




    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get("C:/Users/User/Desktop/naGita/Neuca/uploads").resolve(filename);


            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaTypeFactory.getMediaType(resource).orElseThrow())
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @RequestMapping("/strefaPracownika/potwierdzanieUzytkownikowLekarze")
    public String potwierdzanieUzytkownikowLekarze(Model model){
        List<Lekarz> lekarze=s.lekarze();
        model.addAttribute("lekarze",lekarze);
        return "potwierdzanieUzytkownikowLekarze";
    }

    @RequestMapping("/strefaPracownika/uzytkownikLekarz")
    public String uzytkownikLekarz(@RequestParam Long id, Model model){
        Lekarz lekarz=s.znajdzLekarza(id);
        model.addAttribute("lekarz", lekarz);
        return "uzytkownikLekarz";
    }



    @RequestMapping("/strefaPracownika/uzytkownikLekarzPotwierdz")
    public String uzytkownikLekarzPotwierdz(@RequestParam Long id){
        Lekarz lekarz=s.znajdzLekarza(id);
        s.potwierdzLekarz(lekarz);
        return "uzytkownikLekarzPotwierdz";
    }

    @RequestMapping("/strefaPracownika/uzytkownikLekarzAnuluj")
    public String uzytkownikLekarzAnuluj(@RequestParam Long id){
        Lekarz lekarz=s.znajdzLekarza(id);
        serwisLekarz.usunLekarza(lekarz);
        return "uzytkownikLekarzAnuluj";
    }

    @RequestMapping("/strefaPracownika/potwierdzanieUzytkownikowAptekarze")
    public String potwierdzanieUzytkownikowAptekarze(Model model){
        List<Apteka> apetki=s.apteki();
        model.addAttribute("apteki",apetki);
        return "potwierdzanieUzytkownikowAptekarze";
    }

    @RequestMapping("/strefaPracownika/uzytkownikAptekarz")
    public String uzytkownikAptekarz(Model model, @RequestParam Long id){
        Apteka apteka=serwisApteka.znajdzApteka(id);
        model.addAttribute("apteka",apteka);
        return "uzytkownikAptekarz";
    }

    @RequestMapping("/strefaPracownika/uzytkownikAptekarzPotwierdz")
    public String uzytkownikAptekarzPotwierdz(@RequestParam Long id){
        Apteka apteka=serwisApteka.znajdzApteka(id);
        serwisApteka.potwierdzApteka(id);
        return "uzytkownikAptekarzPotwierdz";
    }

    @RequestMapping("/strefaPracownika/uzytkownikAptekarzAnuluj")
    public String uzytkownikAptekarzAnuluj(@RequestParam Long id){
        Apteka apteka=serwisApteka.znajdzApteka(id);
        serwisApteka.usunApteke(apteka);
        return "uzytkownikAptekarzAnuluj";
    }


    @RequestMapping("/strefaPracownika/autoryzacja")
    public String autoryzacja(){
        return "autoryzacja";
    }






}