package com.example.otomoto;

public enum Dostawa {
    KURIER("Przesyłka kurierska",1999),
    AUTOMAT("Przesyłka do automatu paczkowego",999),
    POCZTA("Przesyłka pocztowa",1499),
    SKLEP("Dostawa do sklepu stacjonarnego",999),
    ODBIOR("Odbiór osobisty",0);

    private String slowo;
    private int cena;
    Dostawa(String slowo, int cena){
        this.slowo=slowo;
        this.cena=cena;
    }

    public String getSlowo() { // Metoda do pobrania nazwy
        return slowo;
    }
    public int getCena(){
        return cena;
    }

}
