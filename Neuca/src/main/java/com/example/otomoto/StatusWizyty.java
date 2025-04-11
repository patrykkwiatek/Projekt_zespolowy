package com.example.otomoto;

public enum StatusWizyty {
    ZAREZERWOWANA("zarezerwowana"),
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
