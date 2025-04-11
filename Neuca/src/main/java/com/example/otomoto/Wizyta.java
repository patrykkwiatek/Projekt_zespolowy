package com.example.otomoto;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Wizyta {
    @Id
    @GeneratedValue
    private Long id;
    private StatusWizyty statusWizyty;
    private String opis;
    private LocalDateTime data;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "lekarz")
    private Lekarz lekarz;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "myUser")
    private MyUser myUser;






}
