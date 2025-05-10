package com.example.otomoto;

import jakarta.persistence.*;
import lombok.*;


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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "my_user_id")
    private MyUser myUser;


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
}


