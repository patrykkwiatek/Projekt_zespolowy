package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RepoRezerwacjaLeku extends JpaRepository<RezerwacjaLeku, Long> {
    List<RezerwacjaLeku> findByAptekaAndDataOdbioruBefore(Apteka apteka, LocalDate data);
}
