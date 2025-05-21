package com.example.otomoto;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SerwisLekarz {
    RepoLekarz repoLekarz;
    RepoMyUser repoMyUser;
    RepoWizyta repoWizyta;
    SerwisGrafik serwisGrafik;
    SerwisWizyta serwisWizyta;

    SerwisLekarz(SerwisWizyta serwisWizyta, RepoLekarz repoLekarz, RepoMyUser repoMyUser,SerwisGrafik serwisGrafik, RepoWizyta repoWizyta){
        this.repoLekarz=repoLekarz;
        this.repoMyUser=repoMyUser;
        this.serwisGrafik=serwisGrafik;
        this.repoWizyta=repoWizyta;
        this.serwisWizyta=serwisWizyta;
    }



    public Lekarz dodajLekarza(Lekarz lekarz,MyUser myUser){
        lekarz.setMyUser(myUser);
        lekarz.setPotwierdzenie(true);
        Lekarz lekarzzapisany=repoLekarz.save(lekarz);
        myUser.setLekarz(lekarzzapisany);
        repoMyUser.save(myUser);
        List<GrafikLekarz> lista=new ArrayList<>();
        GrafikLekarz pon=new GrafikLekarz(DayOfWeek.MONDAY,true,lekarz);
        lista.add(pon);
        GrafikLekarz wt=new GrafikLekarz(DayOfWeek.TUESDAY,true,lekarz);
        lista.add(wt);
        GrafikLekarz sr=new GrafikLekarz(DayOfWeek.WEDNESDAY,true,lekarz);
        lista.add(sr);
        GrafikLekarz czw=new GrafikLekarz(DayOfWeek.THURSDAY,true,lekarz);
        lista.add(czw);
        GrafikLekarz pt=new GrafikLekarz(DayOfWeek.FRIDAY,true,lekarz);
        lista.add(pt);
        GrafikLekarz sob=new GrafikLekarz(DayOfWeek.SATURDAY,true,lekarz);
        lista.add(sob);
        GrafikLekarz ndz=new GrafikLekarz(DayOfWeek.SUNDAY,true,lekarz);
        lista.add(ndz);
        lekarz.setGrafikLekarz(lista);
        serwisGrafik.zapiszGrafik(pon);
        serwisGrafik.zapiszGrafik(wt);
        serwisGrafik.zapiszGrafik(sr);
        serwisGrafik.zapiszGrafik(czw);
        serwisGrafik.zapiszGrafik(pt);
        serwisGrafik.zapiszGrafik(sob);
        serwisGrafik.zapiszGrafik(ndz);
        repoLekarz.save(lekarz);
        return lekarz;
    }

    public Lekarz dodajLekarzaNie(Lekarz lekarz,MyUser myUser){  //lekarz nie potwierdzony
        lekarz.setMyUser(myUser);
        lekarz.setPotwierdzenie(false);
        Lekarz lekarzzapisany=repoLekarz.save(lekarz);
        myUser.setLekarz(lekarzzapisany);
        repoMyUser.save(myUser);
        List<GrafikLekarz> lista=new ArrayList<>();
        GrafikLekarz pon=new GrafikLekarz(DayOfWeek.MONDAY,true,lekarz);
        lista.add(pon);
        GrafikLekarz wt=new GrafikLekarz(DayOfWeek.TUESDAY,true,lekarz);
        lista.add(wt);
        GrafikLekarz sr=new GrafikLekarz(DayOfWeek.WEDNESDAY,true,lekarz);
        lista.add(sr);
        GrafikLekarz czw=new GrafikLekarz(DayOfWeek.THURSDAY,true,lekarz);
        lista.add(czw);
        GrafikLekarz pt=new GrafikLekarz(DayOfWeek.FRIDAY,true,lekarz);
        lista.add(pt);
        GrafikLekarz sob=new GrafikLekarz(DayOfWeek.SATURDAY,true,lekarz);
        lista.add(sob);
        GrafikLekarz ndz=new GrafikLekarz(DayOfWeek.SUNDAY,true,lekarz);
        lista.add(ndz);
        lekarz.setGrafikLekarz(lista);
        serwisGrafik.zapiszGrafik(pon);
        serwisGrafik.zapiszGrafik(wt);
        serwisGrafik.zapiszGrafik(sr);
        serwisGrafik.zapiszGrafik(czw);
        serwisGrafik.zapiszGrafik(pt);
        serwisGrafik.zapiszGrafik(sob);
        serwisGrafik.zapiszGrafik(ndz);
        repoLekarz.save(lekarz);
        return lekarz;
    }

    public void usunLekarza(Lekarz lekarz){
        List<GrafikLekarz> grafiki=lekarz.getGrafikLekarz();
        for(GrafikLekarz g : grafiki ){
            serwisGrafik.usunGrafik(g);
        }
        List<Wizyta> wizyty=lekarz.getWizyty();
        for(Wizyta w :wizyty){
            serwisWizyta.usunWizyte(w);
        }
        repoLekarz.delete(lekarz);
    }

    public Page<Lekarz> getAllFiltry(int page, int size, String miasto, LekarzSpec spec) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        boolean hasCity;
        if(miasto.equals("")){
            hasCity=false;
            System.out.println("false");
        }else{
            hasCity=true;
            System.out.println("true");
        }
        boolean hasSpec;
        if(spec != LekarzSpec.WSZYSTKIE){
            hasSpec=true;
        }else{
            hasSpec=false;
        }
        if (hasCity==true && hasSpec==true) {
            return repoLekarz.findBySpecAndMiastoContainingIgnoreCase(spec, miasto, pageable);
        } else if (hasCity==true) {
            return repoLekarz.findByMiastoContainingIgnoreCase(miasto, pageable);
        } else if (hasSpec) {
            return repoLekarz.findBySpec(spec, pageable);
        } else {
            return repoLekarz.findAll(pageable);
        }
    }



    public Lekarz editLekarz(Lekarz lekarz, Lekarz lekarzN){
        lekarz.setImie(lekarzN.getImie());
        lekarz.setNazwisko(lekarzN.getNazwisko());
        lekarz.setNazwaGabinetu(lekarzN.getNazwaGabinetu());
        lekarz.setDataUrodzenia(lekarzN.getDataUrodzenia());
        lekarz.setAdres1Gabinetu(lekarzN.getAdres1Gabinetu());
        lekarz.setAdres2Gabinetu(lekarzN.getAdres2Gabinetu());
        lekarz.setMiasto(lekarzN.getMiasto());
        lekarz.setTelefonGabinet(lekarzN.getTelefonGabinet());
        lekarz.setCena(lekarzN.getCena());
        lekarz.setSpec(lekarzN.getSpec());
        lekarz.setSredniCzasWizyty(lekarzN.getSredniCzasWizyty());
        return repoLekarz.save(lekarz);
    }

    public List<Wizyta> zwrocWizyty( Lekarz lekarz) {

        return repoWizyta.findByLekarz(lekarz);
    }

    public List<Wizyta> zwrocWizytyStatus(Lekarz lekarz, StatusWizyty statusWizyty){

        return repoWizyta.findByLekarzAndStatusWizyty(lekarz,statusWizyty);
    }
    void ustawSciezke(String sciezka, Lekarz lekarz){
        lekarz.setFoto(sciezka);
        repoLekarz.save(lekarz);
    }





}
