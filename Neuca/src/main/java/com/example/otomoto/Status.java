package com.example.otomoto;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum Status {
    BRAK("Brak"),
    OCZEKUJE("Oczekujące"),
    WYSlANE("Wysłane"),
    ODEBRANE("Odebrane"),
    ANULOWANE("Anulowane"),
    ZAKONCZONE("Zakończone"),
    PROBLEM("Problemowe"),
    ALL("wszystkie");

    private String slowo;

    Status(String slowo){
        this.slowo=slowo;
    }

    public String getSlowo() { // Metoda do pobrania nazwy
        return slowo;
    }


    public static Status losowy() {
        Status[] wartosci = values();
        List<Status> doLosowania = Arrays.stream(wartosci)
                .filter(s -> s != BRAK && s != ALL)
                .collect(Collectors.toList());
        return doLosowania.get(new Random().nextInt(doLosowania.size()));
    }

    public static Status losujStatus() {
        List<Status> dostepneStatusy = Arrays.stream(Status.values())
                .filter(s -> s != BRAK && s != ALL)
                .collect(Collectors.toList());

        if (dostepneStatusy.isEmpty()) {
            throw new IllegalStateException("Brak dostępnych statusów do wylosowania.");
        }

        return dostepneStatusy.get(new Random().nextInt(dostepneStatusy.size()));
    }



}
