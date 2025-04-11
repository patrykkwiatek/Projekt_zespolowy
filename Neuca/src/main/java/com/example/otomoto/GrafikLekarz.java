package com.example.otomoto;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class GrafikLekarz {
    @Id
    @GeneratedValue
    private Long id;
    private DayOfWeek dzienTygodnia;
    private LocalTime godzinaStart;
    private LocalTime godzinaKoniec;
    private boolean zamkniete;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "grafikLekarz")
    private Lekarz lekarz;

}