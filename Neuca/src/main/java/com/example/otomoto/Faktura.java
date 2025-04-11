package com.example.otomoto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Faktura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean czyFaktura;
    private String nazwaFirmy;
    private String adres1;
    private String adres2;

    private String nip;
    private LocalDateTime dataWystawienia;
    private String displayDateWystawienia;

    @OneToOne(mappedBy = "faktura")
    private Zamowienie zamowienie;

    public Faktura( boolean czyFaktura, String nazwaFirmy, String adres1, String adres2, String nip, LocalDateTime data,  String displayDateWystawienia, Zamowienie zamowienie) {
        this.id = id;
        this.czyFaktura = czyFaktura;
        this.nazwaFirmy = nazwaFirmy;
        this.adres1 = adres1;
        this.adres2 = adres2;
        this.nip = nip;
        this.dataWystawienia = dataWystawienia;
        this.displayDateWystawienia=displayDateWystawienia;
        this.zamowienie = zamowienie;
    }
}
