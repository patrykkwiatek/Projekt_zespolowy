package com.example.otomoto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProduktKoszykApteka {
    @GeneratedValue
    @Id
    private Long id;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "lek")
    private Lek lek;
    private int ilosc;



    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "zamowienieApteka")
    private ZamowienieApteka zamowienieApteka;

    public String cenaString(){
        int razemGR= ilosc*lek.getPriceGR();
        int zlote=razemGR /100;
        int grosze= razemGR % 100;
        String cenaStr=String.format("%d,%02d zł", zlote, grosze);
        return  cenaStr;
    }

}
