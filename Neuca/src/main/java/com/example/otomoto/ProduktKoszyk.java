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
public class ProduktKoszyk {
    @Id
    @GeneratedValue
    private Long id;

    private String cenaJednostkowa;
    private String cenaCalosciowa;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "lek")
    private Lek lek;
    private int ilosc;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "zamowienie")
    private Zamowienie zamowienie;


    public ProduktKoszyk(Lek lek, int ilosc, Zamowienie zamowienie) {
        this.lek = lek;
        this.ilosc = ilosc;
        this.zamowienie = zamowienie;
    }


}
