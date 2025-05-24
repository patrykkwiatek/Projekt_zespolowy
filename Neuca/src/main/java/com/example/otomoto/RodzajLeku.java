package com.example.otomoto;

public enum RodzajLeku {
    DERMATOLOGICZNE("Na problemy skórne"),
    NA_ALERGIE("Na alergie"),
    NA_GARDŁO("Na ból gardła"),
    NA_KASZEL("Na kaszel"),
    NA_PRZEZIĘBIENIE("Na przeziębienie i grypę"),
    PRZECIWBÓLOWE("Przeciwbólowe"),
    PRZECIWBAKTERYJNE("Przeciwbakteryjne"),
    PRZECIWGORĄCZKOWE("Przeciwgorączkowe"),
    PRZECIWGRZYBICZE("Przeciwgrzybicze"),
    PRZECIWHISTAMINOWE("Przeciwhistaminowe"),
    PRZECIWZAPALNE("Przeciwzapalne"),
    WSZYSTKIE("Wszystkie");

    private final String slowo;

    RodzajLeku(String slowo) {
        this.slowo = slowo;
    }

    public String getSlowo() {
        return slowo;
    }
}
