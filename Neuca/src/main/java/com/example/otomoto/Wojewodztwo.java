package com.example.otomoto;

import java.util.Random;

public enum Wojewodztwo {
    DOLNOSLASKIE,
    KUJAWSKO_POMORSKIE,
    LUBELSKIE,
    LUBUSKIE,
    LODZKIE,
    MALOPOLSKIE,
    MAZOWIECKIE,
    OPOLSKIE,
    PODKARPACKIE,
    PODLASKIE,
    POMORSKIE,
    SLASKIE,
    SWIETOKRZYSKIE,
    WARMINSKO_MAZURSKIE,
    WIELKOPOLSKIE,
    ZACHODNIOPOMORSKIE,
    ALL;

    private static final Random RANDOM = new Random();
    public static Wojewodztwo losuj() {
        Wojewodztwo[] wartosci = values();
        int index = RANDOM.nextInt(wartosci.length - 1);
        return wartosci[index];
    }
}
