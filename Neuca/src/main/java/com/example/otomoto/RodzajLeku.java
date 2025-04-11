package com.example.otomoto;

public enum RodzajLeku {
    PRZECIWBÓLOWE("Przeciwbólowe"),
    PRZECIWGORĄCZKOWE("Przeciwgorączkowe"),
    PRZECIWHISTAMINOWE("Przeciwhistaminowe"),
    PRZECIWZAPALNE("Przeciwzapalne"),
    PRZECIWBAKTERYJNE("Przeciwbakteryjne"),
    PRZECIWGRZYBICZE("Przeciwgrzybicze"),
    NA_PRZEZIĘBIENIE("Na przeziębienie i grypę"),
    NA_KASZEL("Na kaszel"),
    NA_GARDŁO("Na ból gardła"),
    NA_ALERGIE("Na alergie"),
    DERMATOLOGICZNE("Na problemy skórne"),
    WSZYSTKIE("Wszystkie");

    private final String slowo;

    RodzajLeku(String slowo) { // Konstruktor enum
        this.slowo = slowo;
    }
    public String getSlowo() { // Metoda do pobrania nazwy
        return slowo;
    }
}