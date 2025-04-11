package com.example.otomoto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDTO {
    private Long id;
    private String imie;
    private String nazwisko;
    private String username ;
    private String email;
    private int phone;
    private boolean plec; // false=men, true=women
    private String password;
    private String confirm;
    private List<String> roles;
    private LekarzSpec lekarzSpec;



}
