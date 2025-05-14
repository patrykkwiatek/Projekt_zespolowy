package com.example.otomoto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LekStanApteka {
    @Id
    @GeneratedValue
    private Long id;

    private int sztuk;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "lek")
    private Lek lek;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "apteka")
    private Apteka apteka;
}
