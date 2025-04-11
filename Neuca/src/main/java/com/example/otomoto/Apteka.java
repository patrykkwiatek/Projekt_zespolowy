package com.example.otomoto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private String adres1;
    private String miasto;
    private String kod;
    private String telefon;
    private Wojewodztwo wojewodztwo;


    public Apteka(String name, String adres1, String miasto, String kod, String telefon, Wojewodztwo wojewodztwo) {
        this.potwierdzenie=false;
        this.name = name;
        this.adres1 = adres1;
        this.miasto = miasto;
        this.kod=kod;
        this.telefon = telefon;
        this.wojewodztwo = wojewodztwo;
    }
}


