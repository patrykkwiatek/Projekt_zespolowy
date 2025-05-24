package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.DayOfWeek;
import java.util.List;

public interface RepoGrafikLekarz extends JpaRepository<GrafikLekarz, Long> {
    GrafikLekarz findByLekarzAndDzienTygodnia(Lekarz lekarz, DayOfWeek dzien);
    List<GrafikLekarz> findByLekarz(Lekarz lekarz);
}
