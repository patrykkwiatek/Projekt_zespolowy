package com.example.otomoto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RezerwacjaLeku {
    @Id
    @GeneratedValue
    private Long id;
    private String imie;
    private String nazwisko;
    private int ilosc;
    private LocalDate dataRezerwacji;
    private LocalDate dataOdbioru;
    private StatusRezerwacji statusRezerwacji;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "apteka")
    private Apteka apteka;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "lek")
    private Lek lek;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "myUser")
    private MyUser myUser;


    public String cenaString(){
        int cenaINT=ilosc *lek.getPriceGR();
        int zlote=cenaINT / 100;
        int grosze =cenaINT % 100;
        String cenaString =String.format("%d,%02d zł",zlote,grosze);
        return cenaString;
    }


}
