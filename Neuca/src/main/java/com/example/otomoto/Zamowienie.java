package com.example.otomoto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Zamowienie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataZamowienia;
    private String displayDateZamowienia;
    private String imie;
    private String nazwisko;
    private String email;
    private int telefon;
    private String adres1;
    private String adres2;
    private Dostawa dostawa;
    private Status status;
    private boolean czyZakonczone = false;
    private String calkowitaCena;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "myuser_id")
    private MyUser myuser;

    @OneToMany(fetch= FetchType.EAGER,mappedBy = "zamowienie")
    private List<ProduktKoszyk> produktKoszyk;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "faktura")
    private Faktura faktura;


}
