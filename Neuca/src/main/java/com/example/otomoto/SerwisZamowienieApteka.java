package com.example.otomoto;

import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SerwisZamowienieApteka {
    private final RepoZamowienieApteka repoZamowienieApteka;
    private final SerwisLekStanApteka serwisLekStanApteka;


    public SerwisZamowienieApteka(SerwisLekStanApteka serwisLekStanApteka, RepoZamowienieApteka repoZamowienieApteka) {
        this.repoZamowienieApteka = repoZamowienieApteka;
        this.serwisLekStanApteka=serwisLekStanApteka;
    }


    public ZamowienieApteka zwrocZamowienieAktualne(Apteka apteka) {
        Optional<ZamowienieApteka> zam = repoZamowienieApteka.findByAptekaAndCzyZakonczone(apteka, false);

        if (zam.isPresent()) {
            return zam.get();
        } else {
            ZamowienieApteka zamowienieAptekaNew = new ZamowienieApteka();
            zamowienieAptekaNew.setApteka(apteka);
            zamowienieAptekaNew.setStatusZamoweniaApteka(StatusZamoweniaApteka.BRAK);
            zamowienieAptekaNew.setCzyZakonczone(false);
            zamowienieAptekaNew.setProduktKoszykApteka(new ArrayList<>());
            return repoZamowienieApteka.save(zamowienieAptekaNew);
        }
    }

    public int sumaKoszyk(ZamowienieApteka zamowienieApteka){
        List<ProduktKoszykApteka> produkty = zamowienieApteka.getProduktKoszykApteka();

        if (produkty == null || produkty.isEmpty()) {
            System.out.println("Brak produktów w koszyku.");
            return 0;
        }

        int suma = 0;
        for (ProduktKoszykApteka p : produkty) {
            if (p.getLek() == null) {
                System.out.println("Produkt bez przypisanego leku!");
                continue;
            }
            if (p.getIlosc() <= 0) {
                System.out.println("Produkt z niepoprawną ilością: " + p.getIlosc());
                continue;
            }
            suma += p.getIlosc() * p.getLek().getPriceGR();
        }
        return suma;
    }

    public void zamow(ZamowienieApteka zamowienieApteka){
        zamowienieApteka.setCzyZakonczone(true);
        zamowienieApteka.setDataZamowienia(LocalDate.now());
        zamowienieApteka.setStatusZamoweniaApteka(StatusZamoweniaApteka.OCZEKUJE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        zamowienieApteka.setDisplayDateZamowienia(zamowienieApteka.getDataZamowienia().format(formatter));
        zamowienieApteka.setCzyZaladowane(false);
        repoZamowienieApteka.save(zamowienieApteka);
    }


    List<ZamowienieApteka> zwrocZamowieniaApteka(Apteka apteka){
        List<ZamowienieApteka> z=repoZamowienieApteka.findByAptekaAndStatusZamoweniaAptekaNot(apteka, StatusZamoweniaApteka.BRAK);
        return z;
    }

    ZamowienieApteka zwrocZamowieniePoId(Long id){
        Optional<ZamowienieApteka> zamowienia=repoZamowienieApteka.findById(id);
        ZamowienieApteka z=zamowienia.get();
        return z;

    }

    public void zmienStatus(ZamowienieApteka zamowienieApteka, StatusZamoweniaApteka s){
        zamowienieApteka.setStatusZamoweniaApteka(s);
        repoZamowienieApteka.save(zamowienieApteka);
    }

    public void zaladujZamowienie(ZamowienieApteka z){
        Apteka apteka=z.getApteka();
        List<ProduktKoszykApteka> lista=z.getProduktKoszykApteka();
        for(ProduktKoszykApteka p :lista){
            Lek lek=p.getLek();
            int ilosc=p.getIlosc();
            serwisLekStanApteka.dodajDoApteki(apteka,ilosc,lek);
        }
        z.setCzyZaladowane(true);
        repoZamowienieApteka.save(z);
    }

}
