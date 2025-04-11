package com.example.otomoto;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SerwisLekarz {
    RepoLekarz repoLekarz;
    RepoMyUser repoMyUser;

    SerwisLekarz(RepoLekarz repoLekarz, RepoMyUser repoMyUser){
        this.repoLekarz=repoLekarz;
        this.repoMyUser=repoMyUser;
    }



    public Lekarz dodajLekarza(Lekarz lekarz,MyUser myUser){
        lekarz.setMyUser(myUser);
        Lekarz lekarzzapisany=repoLekarz.save(lekarz);

        myUser.setLekarz(lekarzzapisany);
        repoMyUser.save(myUser);

        return lekarz;
    }

}
