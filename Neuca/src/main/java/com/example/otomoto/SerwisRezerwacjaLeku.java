package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SerwisRezerwacjaLeku {
    private final RepoRezerwacjaLeku repoRezerwacjaLeku;

    public SerwisRezerwacjaLeku(RepoRezerwacjaLeku repoRezerwacjaLeku) {
        this.repoRezerwacjaLeku = repoRezerwacjaLeku;
    }


    public void dodajRezerwacje(MyUser myUser, Apteka apteka,Lek lek, int ilosc,String imie, String nazwisko){
        RezerwacjaLeku rezerwacjaLeku=new RezerwacjaLeku();
        rezerwacjaLeku.setApteka(apteka);
        rezerwacjaLeku.setLek(lek);
        rezerwacjaLeku.setMyUser(myUser);
        rezerwacjaLeku.setIlosc(ilosc);
        rezerwacjaLeku.setImie(imie);
        rezerwacjaLeku.setNazwisko(nazwisko);
        rezerwacjaLeku.setStatusRezerwacji(StatusRezerwacji.W_TRAKCIE);
        LocalDate dzisiaj = LocalDate.now();
        dzisiaj=dzisiaj.minusDays(1);
        rezerwacjaLeku.setDataRezerwacji(dzisiaj);
        rezerwacjaLeku.setDataOdbioru(dzisiaj.plusDays(2));

        repoRezerwacjaLeku.save(rezerwacjaLeku);
    }

    public List<RezerwacjaLeku> zwrocRezerwacje(LocalDate dataodbioru, Apteka apteka){
        List<RezerwacjaLeku> rezerwacje=repoRezerwacjaLeku.findByAptekaAndDataOdbioruBefore(apteka, dataodbioru);
        return rezerwacje;
    }

    public RezerwacjaLeku zwrocPoId(Long id){
        return repoRezerwacjaLeku.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono rezerwacji o id: " + id));
    }


    public void zmienStatus(StatusRezerwacji statusRezerwacji, RezerwacjaLeku rezerwacjaLeku){
        rezerwacjaLeku.setStatusRezerwacji(statusRezerwacji);
        repoRezerwacjaLeku.save(rezerwacjaLeku);
    }


    List <RezerwacjaLeku> zwrocRezerwacjaUzytkownika(MyUser myUser){
        List<RezerwacjaLeku> lista=repoRezerwacjaLeku.findByMyUser(myUser);
        return lista;
    }





}
