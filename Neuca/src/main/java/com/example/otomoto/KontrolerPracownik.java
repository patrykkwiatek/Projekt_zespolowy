package com.example.otomoto;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
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


    public KontrolerPracownik(SerwisPracownik s,SerwisKoszyk serwisKoszyk,SerwisMyUser serwisMyUser) {
        this.s = s;
        this.serwisKoszyk=serwisKoszyk;
        this.serwisMyUser=serwisMyUser;
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
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "WSZYSTKIE") MarkaLeku markaLeku,
                        @RequestParam(defaultValue = "") String wzorzec,
                        Model model) {
        Page<Lek> wynik = s.getAllDtoLeki(page,size,"Id",wzorzec,0,1000,markaLeku,null,false);
        List<MarkaLeku> marki = Arrays.asList(markaLeku.values());
        model.addAttribute("marki", marki);
        model.addAttribute("lista", wynik);
        model.addAttribute("size", size);
        model.addAttribute("numbers", s.createPageNumbers(page, wynik.getTotalPages()));
        model.addAttribute("pageA",page+1);
        model.addAttribute("wzorzec",wzorzec);
        model.addAttribute("markaLeku", markaLeku);
        return "przegladajLeki";
    }
    @RequestMapping("/strefaPracownika")
    public String strefaPracownika(){
        return "strefaPracownika";
    }

    @RequestMapping("/strefaPracownika/realizujZamowienia")
    public String realizujZamowienia(Model model,
                                     @RequestParam(defaultValue = "ALL") Status status){
        List<Status> statusy = Arrays.stream(Status.values())
                .filter(s -> s != Status.BRAK)
                .collect(Collectors.toList());
        model.addAttribute("statusy",statusy);
        List<Zamowienie> zamowienia=s.pobierzListeZamowien(status);
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
    public String uzytkownikLekarzPotwierdz(Model model,Authentication authentication, @RequestParam Long id){
        Lekarz lekarz=s.znajdzLekarza(id);
        s.potwierdzLekarz(lekarz);
        return "uzytkownikLekarzPotwierdz";
    }





}