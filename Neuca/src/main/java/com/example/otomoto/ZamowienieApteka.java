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



    @OneToMany(fetch= FetchType.EAGER,mappedBy = "zamowienieApteka")
    private List<ProduktKoszykApteka> produktKoszykApteka;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "apteka")
    private Apteka apteka;


}
