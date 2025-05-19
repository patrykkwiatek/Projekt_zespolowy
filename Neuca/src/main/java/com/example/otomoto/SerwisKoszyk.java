package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SerwisKoszyk {
    private final RepoZamowienie repoZamowienie;
    private final RepoProduktKoszyk repoProduktKoszyk;
    private final RepoLek repoLek;

    public SerwisKoszyk(RepoZamowienie repoZamowienie, RepoProduktKoszyk repoProduktKoszyk, RepoLek repoLek) {
        this.repoZamowienie = repoZamowienie;
        this.repoProduktKoszyk = repoProduktKoszyk;
        this.repoLek = repoLek;
    }


    public Zamowienie getAktywnyKoszyk(MyUser user) {
        List<Zamowienie> zamowienia = repoZamowienie.findByMyuserAndCzyZakonczone(user, false);

        if (!zamowienia.isEmpty()) {
            // Zwróć pierwszy koszyk (lub inna logika, np. najnowszy)
            return zamowienia.get(0);
        }

        Zamowienie zamowienie = new Zamowienie();
        zamowienie.setMyuser(user);
        zamowienie.setStatus(Status.BRAK);
        zamowienie.setCzyZakonczone(false);
        zamowienie.setProduktKoszyk(new ArrayList<>());
        return repoZamowienie.save(zamowienie);
    }


    public List<Zamowienie> getZamowienia(MyUser user){
        List<Zamowienie> zamowienia=repoZamowienie.findByMyuserAndStatusNot(user, Status.BRAK);
        return  zamowienia;
    }




    public void dodajDoKoszyka(MyUser user, Lek lek, int ilosc) {
        Zamowienie koszyk = getAktywnyKoszyk(user);


        Optional<ProduktKoszyk> istniejącyProdukt = koszyk.getProduktKoszyk().stream()
                .filter(pk -> pk.getLek().equals(lek))
                .findFirst();

        if (istniejącyProdukt.isPresent()) {
            ProduktKoszyk produktKoszyk = istniejącyProdukt.get();
            produktKoszyk.setIlosc(produktKoszyk.getIlosc() + ilosc);
            repoProduktKoszyk.save(produktKoszyk);
        } else {
            ProduktKoszyk nowyProdukt = new ProduktKoszyk();
            nowyProdukt.setLek(lek);
            nowyProdukt.setIlosc(ilosc);
            nowyProdukt.setZamowienie(koszyk);
            repoProduktKoszyk.save(nowyProdukt);
            koszyk.getProduktKoszyk().add(nowyProdukt);
        }
        repoZamowienie.save(koszyk);
    }


    public void usunZKoszyka(MyUser user, Long produktId) {
        Zamowienie koszyk = getAktywnyKoszyk(user);
        ProduktKoszyk produktKoszyk = koszyk.getProduktKoszyk().stream()
                .filter(pk -> pk.getId().equals(produktId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produkt o podanym ID nie znajduje się w koszyku"));
        koszyk.getProduktKoszyk().remove(produktKoszyk);
        repoProduktKoszyk.delete(produktKoszyk);
        repoZamowienie.save(koszyk);
    }


    public boolean czyKoszykJestPusty(MyUser user){
        Zamowienie zamowienie=getAktywnyKoszyk(user);
        if(zamowienie.getProduktKoszyk().isEmpty()){
            return true;
        }
        return false;
    }



    public void noweZamowienie(Zamowienie zamowienie,MyUser user){
        repoZamowienie.save(zamowienie);
        Zamowienie nowyKoszyk = new Zamowienie();
        nowyKoszyk.setMyuser(user);
        nowyKoszyk.setCzyZakonczone(false);
        nowyKoszyk.setStatus(Status.BRAK);
        nowyKoszyk.setFaktura(new Faktura(false));
        repoZamowienie.save(nowyKoszyk);
    }


}
