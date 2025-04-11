package com.example.otomoto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Uzytkownik {

    @Id
    @GeneratedValue
    private Long id;
    private String imie;
    private String nazwisko;
    private String username ;
    private String email;
    private int phone;
    private boolean plec; // false=men, true=women
    private String password;
    private LekarzSpec lekarzSpec;
    //private List<Zamowienie> zamowienia;


    public Uzytkownik(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
