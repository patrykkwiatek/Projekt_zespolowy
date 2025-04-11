package com.example.otomoto;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lek {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int priceGR;
    private String gramatura;
    private String desc;
    private int minWiek;
    private int maxWiek;
    private boolean oferta;
    private RodzajLeku rodzajLeku;
    private MarkaLeku markaLeku;
    private String sklad;
    private String dawkowanie;
    private int iloscKup;
    private String sciezka;

    @OneToMany(fetch= FetchType.LAZY,mappedBy = "lek")
    private List<ProduktKoszyk> produktKoszyk;


    public Lek(String name, int priceGR, String gramatura, String desc, int minWiek, int maxWiek, boolean oferta, RodzajLeku rodzajLeku,MarkaLeku markaLeku, int iloscKup, String sciezka, String sklad,String dawkowanie) {
        this.name = name;
        this.priceGR = priceGR;
        this.gramatura = gramatura;
        this.desc = desc;
        this.minWiek = minWiek;
        this.maxWiek = maxWiek;
        this.oferta = oferta;
        this.rodzajLeku = rodzajLeku;
        this.markaLeku = markaLeku;
        this.iloscKup = iloscKup;
        this.sklad=sklad;
        this.dawkowanie=dawkowanie;
        this.sciezka = sciezka;
    }

    public String cenaString() {
        int zlote = getPriceGR() / 100;
        int grosze = getPriceGR() % 100;
        return String.format("%d,%02d zł", zlote, grosze);
    }
}