package com.example.otomoto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
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
    private List<String> roles;
    private LekarzSpec lekarzSpec;

    @OneToMany(fetch= FetchType.LAZY,mappedBy = "myuser")
    private List<Zamowienie> zamowienie;

    @OneToMany(fetch= FetchType.LAZY,mappedBy = "myUser")
    private List<Wizyta> wizyty;

    @OneToMany(fetch= FetchType.LAZY,mappedBy = "myUser")
    private List<RezerwacjaLeku> rezerwacje;


    @OneToOne(mappedBy = "myUser")
    private Lekarz lekarz;

    @OneToOne(mappedBy = "myUser")
    private Apteka apteka;


    public MyUser(List<String> roles, String password, boolean plec, int phone, String email, String username, String nazwisko, String imie) {
        this.roles = roles;
        this.password = password;
        this.plec = plec;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.nazwisko = nazwisko;
        this.imie = imie;
    }
}
