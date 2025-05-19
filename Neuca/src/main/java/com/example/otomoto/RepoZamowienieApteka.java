package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoZamowienieApteka extends JpaRepository<ZamowienieApteka, Long> {

    Optional<ZamowienieApteka> findByAptekaAndCzyZakonczone(Apteka apteka , boolean czyZakonczone);
}
