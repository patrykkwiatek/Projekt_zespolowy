package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoZamowienieApteka extends JpaRepository<ZamowienieApteka, Long> {

    Optional<ZamowienieApteka> findByAptekaAndCzyZakonczone(Apteka apteka , boolean czyZakonczone);
    List<ZamowienieApteka> findByAptekaAndStatusZamoweniaAptekaNot(Apteka apteka, StatusZamoweniaApteka statusZamoweniaApteka);
    Optional<ZamowienieApteka> findById(Long id);
    }
