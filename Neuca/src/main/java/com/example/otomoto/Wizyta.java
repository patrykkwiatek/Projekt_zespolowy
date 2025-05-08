package com.example.otomoto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wizyta {
    @Id
    @GeneratedValue
    private Long id;
    private StatusWizyty statusWizyty;
    private String opis;
    private LocalDateTime data;
    private String zalecenia;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "lekarz")
    private Lekarz lekarz;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "myUser")
    private MyUser myUser;






}
