package com.example.otomoto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UzytkownikDTO {
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


}
