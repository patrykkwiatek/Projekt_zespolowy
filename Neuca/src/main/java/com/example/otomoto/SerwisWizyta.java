package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerwisWizyta {
    RepoWizyta repoWizyta;

    public SerwisWizyta(RepoWizyta repoWizyta) {
        this.repoWizyta = repoWizyta;
    }

    Wizyta zwrocWizytaID(Long id){
        return repoWizyta.findById(id).orElse(null);
    }
    void zmienStatus(Long id, StatusWizyty statusWizyty){
        Wizyta wizyta=repoWizyta.findById(id).orElse(null);
        wizyta.setStatusWizyty(statusWizyty);
        repoWizyta.save(wizyta);
    }

    void zapiszWizyte(Wizyta wizyta){
        repoWizyta.save(wizyta);
    }

    void usunWizyte(Wizyta wizyta){
        repoWizyta.delete(wizyta);
    }

    List<Wizyta> zwrocMojeWizyty(MyUser myUser){
        List<Wizyta> lista=repoWizyta.findByMyUser(myUser);
        return  lista;
    }

}
