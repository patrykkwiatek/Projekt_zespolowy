package com.example.otomoto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Apteka {
    @Id
    @GeneratedValue
    private Long id;
    private boolean potwierdzenie;
    private String name;
    private String ulica;
    private String numerBud;
    private String numerLokalu;
    private String kodPocztowy;
    private String miasto;
    private String telefon;
    private Wojewodztwo wojewodztwo;

    private boolean pon;
    private boolean wt;
    private boolean sr;
    private boolean czw;
    private boolean pt;
    private boolean sb;
    private boolean ndz;

    private LocalTime ponStart;
    private LocalTime ponStop;
    private LocalTime wtStart;
    private LocalTime wtStop;
    private LocalTime srStart;
    private LocalTime srStop;
    private LocalTime czwStart;
    private LocalTime czwStop;
    private LocalTime ptStart;
    private LocalTime ptStop;
    private LocalTime sbStart;
    private LocalTime sbStop;
    private LocalTime ndzStart;
    private LocalTime ndzStop;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "my_user_id")
    private MyUser myUser;

    @OneToMany(fetch= FetchType.EAGER,mappedBy = "apteka")
    private List<LekStanApteka> lekStanApteka;

    @OneToMany(fetch= FetchType.LAZY,mappedBy = "apteka")
    private List<RezerwacjaLeku> rezerwacje;

    @OneToMany(fetch= FetchType.LAZY,mappedBy = "apteka")
    private List<ZamowienieApteka> zamowienieApteka;




    public Apteka(Long id, boolean potwierdzenie, String numerBud, String numerLokalu, String kodPocztowy, String miasto, String telefon, Wojewodztwo wojewodztwo) {
        this.id = id;
        this.potwierdzenie = potwierdzenie;
        this.numerBud = numerBud;
        this.numerLokalu = numerLokalu;
        this.kodPocztowy = kodPocztowy;
        this.miasto = miasto;
        this.telefon = telefon;
        this.wojewodztwo = wojewodztwo;
    }

    public Apteka( boolean potwierdzenie, String name, String ulica, String numerBud, String numerLokalu, String kodPocztowy, String miasto, String telefon, Wojewodztwo wojewodztwo) {
        this.potwierdzenie = potwierdzenie;
        this.name = name;
        this.ulica = ulica;
        this.numerBud = numerBud;
        this.numerLokalu = numerLokalu;
        this.kodPocztowy = kodPocztowy;
        this.miasto = miasto;
        this.telefon = telefon;
        this.wojewodztwo = wojewodztwo;
    }

}


