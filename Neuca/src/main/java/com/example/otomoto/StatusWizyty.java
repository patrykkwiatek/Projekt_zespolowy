package com.example.otomoto;

public enum StatusWizyty {
    ZAREZERWOWANABRAKPOTW("zarezerwowana, nie potwierdzona przez lekarza"),
    ZAREZERWOWANAPOTW("zarezerwowana, potwierdzona przez lekarza"),
    ANULOWANA("anulowana"),
    ZREALIZOWANA("zrealizowana");

    private String slowo;
    StatusWizyty(String slowo){
        this.slowo=slowo;
    }
    public String getSlowo(){
        return slowo;
    }
}
