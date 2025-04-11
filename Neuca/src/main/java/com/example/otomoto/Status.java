package com.example.otomoto;

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



}
