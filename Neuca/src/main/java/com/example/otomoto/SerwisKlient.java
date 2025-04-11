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


    public Page<Apteka> getAllDto(int page, int size, String sort, String wzorzec, Wojewodztwo wojewodztwo) {
        Pageable pageable;
        Sort sortowanie;

        // Ustalanie rodzaju sortowania
        if ("nameM".equals(sort)) {
            sortowanie = Sort.by("name").ascending();
        } else if ("nameR".equals(sort)) {
            sortowanie = Sort.by("name").descending();
        } else {
            sortowanie = Sort.by("Id").ascending();
        }
        pageable = PageRequest.of(page, size, sortowanie);

        // Filtrowanie po województwie i wzorcu
        if (wojewodztwo != Wojewodztwo.ALL) {
            if (!wzorzec.isEmpty()) {
                return repoApteka.findByWojewodztwoAndNameContainingIgnoreCase(wojewodztwo, wzorzec, pageable);
            }
            return repoApteka.findByWojewodztwo(wojewodztwo, pageable);
        }


        if (!wzorzec.isEmpty()) {
            return repoApteka.findByNameContainingIgnoreCase(wzorzec, pageable);
        }

        return repoApteka.findAll(pageable);
    }

    public Lek zwrocLek(Long lekId){
        Lek lek=repoLek.findById(lekId).orElseThrow(() -> new RuntimeException("Lek nie znaleziony"));
        return lek;
    }


}
