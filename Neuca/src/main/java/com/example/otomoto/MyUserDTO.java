package com.example.otomoto;




import jakarta.validation.constraints.*;
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
    @Size(min = 2, max = 20, message = "Imię musi mieć od 2 do 20 znaków")
    private String imie;
    @Size(min = 2, max = 20, message = "Nazwisko musi mieć od 2 do 20 znaków")
    private String nazwisko;
    @Size(min = 2, max = 20, message = "Login musi mieć od 2 do 20 znaków")
    private String username ;
    @Size(min=2,message = "Podałeś zbyt krótki adres Email")
    @Email(message = "Email musi być poprawny")
    private String email;
    @Min(value = 100000000, message = "Numer telefonu musi mieć co najmniej 9 cyfr")
    @Max(value = 999999999, message = "Numer telefonu nie może mieć więcej niż 9 cyfr")
    private int phone;
    private boolean plec; // false=men, true=women

    @Size(min=6,max=20, message = "Hasło musi mieć od 6 do 20 znaków")
    private String password;
    private String confirm;
    private List<String> roles;
    private LekarzSpec lekarzSpec;



}
