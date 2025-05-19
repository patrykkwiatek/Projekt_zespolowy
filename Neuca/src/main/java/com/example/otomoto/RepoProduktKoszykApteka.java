package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoProduktKoszykApteka extends JpaRepository<ProduktKoszykApteka, Long> {
    Optional<ProduktKoszykApteka> findByZamowienieAptekaAndLek(ZamowienieApteka zamowienieApteka, Lek lek);
    Optional<ProduktKoszykApteka> findById(Long id);


}
