package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class SerwisApteka {
    RepoApteka repoApteka;
    RepoMyUser repoMyUser;

    public SerwisApteka(RepoApteka repoApteka, RepoMyUser repoMyUser) {
        this.repoApteka = repoApteka;
        this.repoMyUser = repoMyUser;
    }


    public void dodajApteke(MyUser myUser, Apteka apteka){
        apteka.setMyUser(myUser);
        apteka.setPotwierdzenie(false);
        apteka=zamknijApteke(apteka);
        Apteka aptekaZapisana=repoApteka.save(apteka);
        myUser.setApteka(aptekaZapisana);
        repoMyUser.save(myUser);
    }
    public void usunApteke(Apteka apteka){
        repoApteka.delete(apteka);
    }

    public Apteka znajdzApteka(Long id){
        Optional<Apteka> apteka=repoApteka.findById(id);
        Apteka apteka1=apteka.get();
        return apteka1;
    }

    public void potwierdzApteka(Long id){
        Apteka apteka=znajdzApteka(id);
        apteka.setPotwierdzenie(true);
        repoApteka.save(apteka);
    }
    Apteka  zamknijApteke(Apteka apteka){
        apteka.setPon(false);
        apteka.setWt(false);
        apteka.setSr(false);
        apteka.setCzw(false);
        apteka.setPt(false);
        apteka.setSb(false);
        apteka.setNdz(false);
        apteka.setPonStart(LocalTime.of(0,0));
        apteka.setPonStop(LocalTime.of(0,0));
        apteka.setWtStart(LocalTime.of(0,0));
        apteka.setWtStop(LocalTime.of(0,0));
        apteka.setSrStart(LocalTime.of(0,0));
        apteka.setSrStop(LocalTime.of(0,0));
        apteka.setCzwStart(LocalTime.of(0,0));
        apteka.setCzwStop(LocalTime.of(0,0));
        apteka.setPtStart(LocalTime.of(0,0));
        apteka.setPtStop(LocalTime.of(0,0));
        apteka.setSbStart(LocalTime.of(0,0));
        apteka.setSbStop(LocalTime.of(0,0));
        apteka.setNdzStart(LocalTime.of(0,0));
        apteka.setNdzStop(LocalTime.of(0,0));
        return apteka;

    }
    public void zmienGodziny(Apteka apteka, LocalTime start, LocalTime stop, int dzien, boolean czy){
        if(dzien == 1) {
            if(czy==true){
                apteka.setPon(true);
                apteka.setPonStart(start);
                apteka.setPonStop(stop);
            }else{
                apteka.setPon(false);
                apteka.setPonStart(LocalTime.of(0,0));
                apteka.setPonStop(LocalTime.of(0,0));
            }

        } else if(dzien == 2) {
            if(czy==true){
                apteka.setWt(true);
                apteka.setWtStart(start);
                apteka.setWtStop(stop);
            }else{
                apteka.setWt(false);
                apteka.setWtStart(LocalTime.of(0,0));
                apteka.setWtStop(LocalTime.of(0,0));
            }
        } else if(dzien == 3) {
            if(czy==true){
                apteka.setSr(true);
                apteka.setSrStart(start);
                apteka.setSrStop(stop);
            }else{
                apteka.setSr(false);
                apteka.setSrStart(LocalTime.of(0,0));
                apteka.setSrStop(LocalTime.of(0,0));
            }
        } else if(dzien == 4) {
            if(czy==true){
                apteka.setCzw(true);
                apteka.setCzwStart(start);
                apteka.setCzwStop(stop);
            }else{
                apteka.setCzw(false);
                apteka.setCzwStart(LocalTime.of(0,0));
                apteka.setCzwStop(LocalTime.of(0,0));
            }
        } else if(dzien == 5) {
            if(czy==true){
                apteka.setPt(true);
                apteka.setPtStart(start);
                apteka.setPtStop(stop);
            }else{
                apteka.setPt(false);
                apteka.setPtStart(LocalTime.of(0,0));
                apteka.setPtStop(LocalTime.of(0,0));
            }
        } else if(dzien == 6) {
            if(czy==true){
                apteka.setSb(true);
                apteka.setSbStart(start);
                apteka.setSbStop(stop);
            }else{
                apteka.setSb(false);
                apteka.setSbStart(LocalTime.of(0,0));
                apteka.setSbStop(LocalTime.of(0,0));
            }
        } else if(dzien == 7) {
            if(czy==true){
                apteka.setNdz(true);
                apteka.setNdzStart(start);
                apteka.setNdzStop(stop);
            }else{
                apteka.setNdz(false);
                apteka.setNdzStart(LocalTime.of(0,0));
                apteka.setNdzStop(LocalTime.of(0,0));
            }
        }
        repoApteka.save(apteka);
    }

    void editApteka(Apteka stara, Apteka nowa ){
        stara.setName(nowa.getName());
        stara.setUlica(nowa.getUlica());
        stara.setNumerBud(nowa.getNumerBud());
        stara.setNumerLokalu(nowa.getNumerLokalu());
        stara.setKodPocztowy(nowa.getKodPocztowy());
        stara.setMiasto(nowa.getMiasto());
        stara.setWojewodztwo(nowa.getWojewodztwo());
        stara.setTelefon(nowa.getTelefon());
        repoApteka.save(stara);
    }


}
