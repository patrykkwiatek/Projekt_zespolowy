package com.example.otomoto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SerwisKlient {
    private RepoLek repoLek;
    private RepoApteka repoApteka;


    public SerwisKlient(RepoLek repoLek, RepoApteka repoApteka) {
        this.repoLek = repoLek;
        this.repoApteka=repoApteka;
    }




    public Lek zwrocLek(Long lekId){
        Lek lek=repoLek.findById(lekId).orElseThrow(() -> new RuntimeException("Lek nie znaleziony"));
        return lek;
    }


}
