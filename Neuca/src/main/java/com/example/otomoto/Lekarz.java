package com.example.otomoto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lekarz {
    @Id
    @GeneratedValue
    private Long id;
    private String imie;
    private String nazwisko;
    private String nazwaGabinetu;
    private LocalDate dataUrodzenia;
    private String adres1Gabinetu;
    private String adres2Gabinetu;
    private String miasto;
    private int telefonGabinet;
    private  int cena;
    private LekarzSpec spec;
    private int sredniCzasWizyty;

    private boolean potwierdzenie=false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "my_user_id")
    private MyUser myUser;

    @OneToMany(fetch= FetchType.LAZY,mappedBy = "lekarz")
    private List<GrafikLekarz> grafikLekarz;


    @OneToMany(fetch= FetchType.LAZY,mappedBy = "lekarz")
    private List<Wizyta> wizyty;






}
