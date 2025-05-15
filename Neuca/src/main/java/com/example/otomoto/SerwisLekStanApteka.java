package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerwisLekStanApteka {
    RepoLekStanApteka repoLekStanApteka;

    public SerwisLekStanApteka(RepoLekStanApteka repoLekStanApteka) {
        this.repoLekStanApteka = repoLekStanApteka;
    }

    void dodajDoApteki(Apteka apteka, int sztuk, Lek lek){
        Optional<LekStanApteka> stan=repoLekStanApteka.findByLekAndApteka(lek,apteka);
        if(stan.isEmpty()){
            LekStanApteka lekStanApteka= new LekStanApteka();
            lekStanApteka.setLek(lek);
            lekStanApteka.setApteka(apteka);
            lekStanApteka.setSztuk(sztuk);
            repoLekStanApteka.save(lekStanApteka);
        }else{
            LekStanApteka lekStanApteka=stan.get();
            int ile=lekStanApteka.getSztuk();
            ile+=sztuk;
            lekStanApteka.setSztuk(ile);
            repoLekStanApteka.save(lekStanApteka);
        }
    }

    List<LekStanApteka> zwrocStan(Apteka apteka){
        List<LekStanApteka> leki=repoLekStanApteka.findByApteka(apteka);
        return  leki;
    }


    public LekStanApteka zwrocPoId(Long id){
        Optional<LekStanApteka> lekStanApteka=repoLekStanApteka.findById(id);
        LekStanApteka lek=lekStanApteka.get();
        return lek;
    }

    public LekStanApteka zwrocPoAptekaILek(Apteka apteka, Lek lek){
        Optional<LekStanApteka> leki= repoLekStanApteka.findByLekAndApteka(lek,apteka);
        LekStanApteka lekStanApteka=leki.get();
        return lekStanApteka;
    }

    public void zapiszStan(LekStanApteka lekStanApteka, int ilosc){
        lekStanApteka.setSztuk(ilosc);
        repoLekStanApteka.save(lekStanApteka);
    }

    public void usunStan(LekStanApteka lekStanApteka){
        repoLekStanApteka.delete(lekStanApteka);
    }


    public List<LekStanApteka> zwrocTamGdzieLek(Lek lek){
        return repoLekStanApteka.findByLek(lek);
    }
}
