package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;

public interface RepoGrafikLekarz extends JpaRepository<GrafikLekarz, Long> {
    GrafikLekarz findByLekarzAndDzienTygodnia(Lekarz lekarz, DayOfWeek dzien);
}
