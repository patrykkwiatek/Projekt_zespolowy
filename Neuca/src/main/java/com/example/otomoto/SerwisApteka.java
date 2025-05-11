package com.example.otomoto;


import org.springframework.stereotype.Service;

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
        Apteka aptekaZapisana=repoApteka.save(apteka);
        myUser.setApteka(aptekaZapisana);
        repoMyUser.save(myUser);
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

}
