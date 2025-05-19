package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SerwisProduktKoszykApteka {
    private  final RepoProduktKoszykApteka repoProduktKoszykApteka;


    public SerwisProduktKoszykApteka(RepoProduktKoszykApteka repoProduktKoszykApteka) {
        this.repoProduktKoszykApteka = repoProduktKoszykApteka;
    }

    public void dodaj(ZamowienieApteka zamowienieApteka, Lek lek, int ile) {
        Optional<ProduktKoszykApteka> produktKoszykApteka =
                repoProduktKoszykApteka.findByZamowienieAptekaAndLek(zamowienieApteka, lek);

        if (produktKoszykApteka.isEmpty()) {
            ProduktKoszykApteka pNew = new ProduktKoszykApteka();
            pNew.setLek(lek);
            pNew.setIlosc(ile);
            pNew.setZamowienieApteka(zamowienieApteka);
            repoProduktKoszykApteka.save(pNew);
        } else {
            ProduktKoszykApteka p = produktKoszykApteka.get();
            p.setIlosc(ile + p.getIlosc());
            repoProduktKoszykApteka.save(p);
        }
    }

    public ProduktKoszykApteka zwrocProdukt(Long id){
        return repoProduktKoszykApteka.findById(id).orElseThrow(() -> new IllegalArgumentException("produkt o ID " + id + " nie został znaleziony."));
    }

    public void usun(ProduktKoszykApteka produktKoszykApteka){
        repoProduktKoszykApteka.delete(produktKoszykApteka);
    }






}
