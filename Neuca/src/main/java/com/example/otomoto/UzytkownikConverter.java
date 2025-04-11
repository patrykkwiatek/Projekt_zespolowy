package com.example.otomoto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UzytkownikConverter {
    public UzytkownikDTO createUzytkownikDTO(Uzytkownik uzytkownik){
        UzytkownikDTO uzytkownikDTO= new UzytkownikDTO();
        uzytkownikDTO.setId(uzytkownik.getId());
        uzytkownikDTO.setImie(uzytkownik.getImie());
        uzytkownikDTO.setNazwisko(uzytkownik.getNazwisko());
        uzytkownikDTO.setUsername(uzytkownik.getUsername());
        uzytkownikDTO.setEmail(uzytkownik.getEmail());
        uzytkownikDTO.setPhone(uzytkownik.getPhone());
        uzytkownikDTO.setPlec(uzytkownik.isPlec());
        uzytkownikDTO.setPassword(uzytkownik.getPassword());
        uzytkownikDTO.setLekarzSpec(uzytkownik.getLekarzSpec());
        //uzytkownikDTO.setZamowienia(uzytkownik.getZamowienia());
        return uzytkownikDTO;
    }


    public Uzytkownik createUzytkownik(UzytkownikDTO uzytkownikDTO){
        Uzytkownik uzytkownik= new Uzytkownik();
        uzytkownik.setId(uzytkownikDTO.getId());
        uzytkownik.setImie(uzytkownikDTO.getImie());
        uzytkownik.setNazwisko(uzytkownikDTO.getNazwisko());
        uzytkownik.setUsername(uzytkownikDTO.getUsername());
        uzytkownik.setEmail(uzytkownikDTO.getEmail());
        uzytkownik.setPhone(uzytkownikDTO.getPhone());
        uzytkownik.setPlec(uzytkownikDTO.isPlec());
        uzytkownik.setPassword(uzytkownikDTO.getPassword());
        uzytkownik.setLekarzSpec(uzytkownikDTO.getLekarzSpec());
        //uzytkownik.setZamowienia(uzytkownikDTO.getZamowienia());
        return uzytkownik;
    }

    public List<UzytkownikDTO> createDTO(List<Uzytkownik> list){
        List<UzytkownikDTO> listaDTO=list.stream().map(this::createUzytkownikDTO).collect(Collectors.toList());
        return listaDTO;
    }

}

