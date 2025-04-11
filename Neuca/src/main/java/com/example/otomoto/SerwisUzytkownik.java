package com.example.otomoto;

import java.util.List;

public class SerwisUzytkownik {
    private final RepoUzytkownik repoUzytkownik;
    private final UzytkownikConverter uzytkownikConverter;

    public SerwisUzytkownik(RepoUzytkownik repoUzytkownik, UzytkownikConverter uzytkownikConverter) {
        this.repoUzytkownik = repoUzytkownik;
        this.uzytkownikConverter = uzytkownikConverter;
    }

    public List<UzytkownikDTO> getALLDTO(){
        return uzytkownikConverter.createDTO(repoUzytkownik.findAll());
    }





}
