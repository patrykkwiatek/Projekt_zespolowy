package com.example.otomoto;

import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SerwisZamowienieApteka {
    private final RepoZamowienieApteka repoZamowienieApteka;


    public SerwisZamowienieApteka(RepoZamowienieApteka repoZamowienieApteka) {
        this.repoZamowienieApteka = repoZamowienieApteka;
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

}
