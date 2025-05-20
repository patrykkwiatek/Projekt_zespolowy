package com.example.otomoto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZamowienieApteka {
    @GeneratedValue
    @Id
    private Long id;
    private LocalDate dataZamowienia;
    private String displayDateZamowienia;
    private StatusZamoweniaApteka statusZamoweniaApteka;
    private boolean czyZakonczone = false;
    private boolean czyZaladowane=false;



    @OneToMany(fetch= FetchType.EAGER,mappedBy = "zamowienieApteka")
    private List<ProduktKoszykApteka> produktKoszykApteka;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "apteka")
    private Apteka apteka;

    public String cenaALL(){
        List<ProduktKoszykApteka> produkty=produktKoszykApteka;
        int suma=0;
        for(ProduktKoszykApteka p :produkty){
            suma+= (p.getIlosc() *p.getLek().getPriceGR());
        }
        int zlote=suma/100;
        int grosze=suma%100;
        String ile=String.format("%d,%02d zł",zlote,grosze);
        return ile;
    }

}
