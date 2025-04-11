package com.example.otomoto;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerwisPracownik {
    private final RepoLek r;
    private final RepoZamowienie repoZamowienie;
    private final RepoLekarz repoLekarz;
    public int numbers=3;

    public SerwisPracownik(RepoLek r,RepoZamowienie repoZamowienie,RepoLekarz repoLekarz) {
        this.repoZamowienie=repoZamowienie;
        this.r = r;
        this.repoLekarz=repoLekarz;
    }


    public Lek DodajLek(Lek lek){
        r.save(lek);
        return  lek;
    }

    public List<Lek> getALL(){
        return r.findAll();
    }

    public Lek findbyID(Long id){
        return  r.findById(id).orElseThrow(() -> new IllegalArgumentException("Lek o ID " + id + " nie został znaleziony."));
    }



    public Lek updateLek(Long id, Lek updatedLek) {
        Lek existingLek = r.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lek o ID " + id + " nie został znaleziony."));
        existingLek.setPriceGR(updatedLek.getPriceGR());
        existingLek.setName(updatedLek.getName());
        existingLek.setGramatura(updatedLek.getGramatura());
        existingLek.setDesc(updatedLek.getDesc());
        existingLek.setMinWiek(updatedLek.getMinWiek());
        existingLek.setMaxWiek(updatedLek.getMaxWiek());
        existingLek.setOferta(updatedLek.isOferta());
        existingLek.setMarkaLeku(updatedLek.getMarkaLeku());
        existingLek.setRodzajLeku(updatedLek.getRodzajLeku());
        existingLek.setSklad(updatedLek.getSklad());
        existingLek.setDawkowanie(updatedLek.getDawkowanie());
        existingLek.setSciezka(updatedLek.getSciezka());

        return r.save(existingLek);
    }
    public void deleteLek(Long id){
        if (!r.existsById(id)) {
            throw new IllegalArgumentException("Lek o ID " + id + " nie został znaleziony.");
        }
         r.deleteById(id);
    }


    public List<Integer> createPageNumbers(int page, int totalPages) {
        List<Integer> pageNumbers = new ArrayList<>();
        if(totalPages <= numbers * 2) {
            for(int i = 0; i < totalPages; ++i) {
                pageNumbers.add(i);
            }
        } else {
            List<Integer> firstPages = new ArrayList<>();
            List<Integer> middlePages = new ArrayList<>();
            List<Integer> lastPages = new ArrayList<>();
            firstPages.add(0);
            firstPages.add(1);
            firstPages.add(2);
            lastPages.add(totalPages - 3);
            lastPages.add(totalPages - 2);
            lastPages.add(totalPages - 1);



            if((page != totalPages - 1) && (page != 0)){
                if(page - 1 > numbers) {
                    middlePages.add(-1);
                }
                middlePages.add(page - 1);
                middlePages.add(page);
                if(page + 1 < totalPages){
                    middlePages.add(page + 1);
                }
                if(page + 1 < totalPages - 3 - 1) {
                    middlePages.add(-1);
                }
            } else {
                middlePages.add(-1);
            }
            pageNumbers.addAll(firstPages);
            for(Integer i : middlePages) {
                if(i == -1) {
                    pageNumbers.add(i);
                } else if(!pageNumbers.contains(i)){
                    pageNumbers.add(i);
                }
            }
            for(Integer i : lastPages) {
                if(i == -1) {
                    pageNumbers.add(i);
                } else if(!pageNumbers.contains(i)){
                    pageNumbers.add(i);
                }
            }
        }
        return pageNumbers;
    }

    public Page<Lek> getAllDto(int page, int size, String sort, String wzorzec) {
        Pageable pageable;
        Sort sortowanie;
        if("Cros".equals(sort)) {
            sortowanie = Sort.by("price").ascending();
        } else if("Cmal".equals(sort)) {
            sortowanie = Sort.by("price").descending();
        } else if("Aros".equals(sort)){
            sortowanie = Sort.by("name").ascending();
        } else if("Amal".equals(sort)) {
            sortowanie = Sort.by("name").descending();
        }else{
            sortowanie=Sort.by("Id").ascending();
        }
        pageable = PageRequest.of(page, size, sortowanie);

        if (!wzorzec.isEmpty()) {
            return r.findByNameContainingIgnoreCase(wzorzec, pageable);
        }
        return r.findAll(pageable);
    }

    public Page<Lek> getAllDtoLeki(int page, int  size,String sort, String wzorzec,int  minPrize, int  maxPrize,MarkaLeku markaLeku, RodzajLeku rodzajLeku,boolean oferta){
        Pageable pageable;
        Sort sortowanie;
        if("Cros".equals(sort)) {
            sortowanie = Sort.by("priceGR").ascending();
        } else if("Cmal".equals(sort)) {
            sortowanie = Sort.by("priceGR").descending();
        } else if("Aros".equals(sort)){
            sortowanie = Sort.by("name").ascending();
        } else if("Amal".equals(sort)) {
            sortowanie = Sort.by("name").descending();
        }else{
            sortowanie=Sort.by("id").ascending();
        }
        pageable = PageRequest.of(page, size, sortowanie);
        Specification<Lek> spec = new LekSpecification(wzorzec, minPrize*100, maxPrize*100, markaLeku, rodzajLeku,oferta);
        return r.findAll(spec, pageable);
    }
    public List<Zamowienie> pobierzListeZamowien(Status status) {
        if (status == Status.ALL) {
            return repoZamowienie.findAll()
                    .stream()
                    .filter(z -> z.getStatus() != Status.BRAK)
                    .collect(Collectors.toList());
        }
        if (status == Status.BRAK) {
            return Collections.emptyList();
        }
        return repoZamowienie.findByStatus(status);
    }


    public Zamowienie zwrocZamowienie(Long id) {
        return repoZamowienie.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zamówienie o ID " + id + " nie istnieje"));
    }

    public void zmienStatusZamowienia(Zamowienie zamowienie){
        Status status=zamowienie.getStatus();
        if(status==Status.OCZEKUJE){
            zamowienie.setStatus(Status.WYSlANE);
        }
        repoZamowienie.save(zamowienie);
    }

    public List<Lekarz> lekarze(){
        List<Lekarz> lekarze= repoLekarz.findByPotwierdzenie(false);
        return lekarze;
    }

    public Lekarz znajdzLekarza(Long id){
        Optional<Lekarz> lekarz=repoLekarz.findById(id);
        Lekarz lekarz1=lekarz.get();
        return lekarz1;
    }

    public Lekarz potwierdzLekarz(Lekarz lekarz){
        lekarz.setPotwierdzenie(true);
        repoLekarz.save(lekarz);
        return lekarz;
    }





}