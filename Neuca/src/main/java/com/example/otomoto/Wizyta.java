package com.example.otomoto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getGodzinaWizyty() {
        if (data != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return data.format(formatter);
        }
        return "";
    }

    public String getDataWizyty() {
        if (data != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return data.format(formatter);
        }
        return "";
    }






}
