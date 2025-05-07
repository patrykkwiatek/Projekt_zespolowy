package com.example.otomoto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GrafikLekarz {
    @Id
    @GeneratedValue
    private Long id;
    private DayOfWeek dzienTygodnia;
    private LocalTime godzinaStart;
    private LocalTime godzinaKoniec;
    private boolean zamkniete;
    private List<Integer> godziny;

    public GrafikLekarz(DayOfWeek dzienTygodnia, boolean zamkniete, Lekarz lekarz) {
        this.dzienTygodnia = dzienTygodnia;
        this.zamkniete = zamkniete;
        this.lekarz = lekarz;
    }



    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "grafikLekarz")
    private Lekarz lekarz;



}