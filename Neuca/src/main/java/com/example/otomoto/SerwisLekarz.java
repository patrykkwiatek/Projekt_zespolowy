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

    SerwisLekarz(RepoLekarz repoLekarz, RepoMyUser repoMyUser,SerwisGrafik serwisGrafik, RepoWizyta repoWizyta){
        this.repoLekarz=repoLekarz;
        this.repoMyUser=repoMyUser;
        this.serwisGrafik=serwisGrafik;
        this.repoWizyta=repoWizyta;
    }



    public Lekarz dodajLekarza(Lekarz lekarz,MyUser myUser){
        lekarz.setMyUser(myUser);
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

    public Page<Wizyta> zwrocWizyty(int page, String sort, Lekarz lekarz) {
        Pageable pageable;
        Sort sortowanie;
        if ("dros".equals(sort)) {
            sortowanie = Sort.by("data").ascending();
        } else if ("dmal".equals(sort)) {
            sortowanie = Sort.by("data").descending();
        } else {
            sortowanie = Sort.by("id").ascending();
        }
        pageable = PageRequest.of(page, 10, sortowanie);

        return repoWizyta.findByLekarz(lekarz,pageable);
    }

    public Page<Wizyta> zwrocWizytyStatus(int page, String sort, Lekarz lekarz, StatusWizyty statusWizyty){
        Pageable pageable;
        Sort sortowanie;
        if ("dros".equals(sort)) {
            sortowanie = Sort.by("data").ascending();
        } else if ("dmal".equals(sort)) {
            sortowanie = Sort.by("data").descending();
        } else {
            sortowanie = Sort.by("id").ascending();
        }
        pageable = PageRequest.of(page, 15, sortowanie);
        return repoWizyta.findByLekarzAndStatusWizyty(lekarz,statusWizyty,pageable);
    }
    void ustawSciezke(String sciezka, Lekarz lekarz){
        lekarz.setFoto(sciezka);
        repoLekarz.save(lekarz);
    }



}
