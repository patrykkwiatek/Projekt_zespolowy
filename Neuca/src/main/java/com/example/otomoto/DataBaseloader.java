package com.example.otomoto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataBaseloader implements CommandLineRunner {
    private final RepoLek repolek;
    private final RepoMyUser repoMyUser;
    private final RepoApteka repoApteka;
    private final RepoUzytkownik repoUzytkownik;
    private final PasswordEncoder passwordEncoder;


    public DataBaseloader(RepoLek repolek, RepoMyUser repoMyUser, RepoUzytkownik repoUzytkownik, PasswordEncoder passwordEncoder,RepoApteka repoApteka) {
        this.repolek = repolek;
        this.repoMyUser = repoMyUser;
        this.repoUzytkownik = repoUzytkownik;
        this.passwordEncoder = passwordEncoder;
        this.repoApteka=repoApteka;
    }

    @Override
    public void run(String... args) throws Exception {
        leki();
        //apteki();
        MyUser user=new MyUser();
        user.setUsername("patrykP");
        user.setPassword(passwordEncoder.encode("haslo"));
        user.setRoles(new ArrayList<>());
        user.getRoles().add("PRACOWNIK");
        user.setImie("Patryk");
        user.setNazwisko("Kwiatkowski");
        user.setEmail("patryk@wp.pl");
        user.setPhone(794291002);
        repoMyUser.save(user);
        MyUser user1=new MyUser();
        user1.setUsername("patrykK");
        user1.setPassword(passwordEncoder.encode("haslo"));
        user1.setRoles(new ArrayList<>());
        user1.getRoles().add("PACJENT");
        repoMyUser.save(user1);

        MyUser lekarz=new MyUser();
        lekarz.setUsername("lekarz");
        lekarz.setPassword(passwordEncoder.encode("lekarz"));
        lekarz.setRoles(new ArrayList<>());
        lekarz.getRoles().add("LEKARZ");
        repoMyUser.save(lekarz);

    }




    private void leki(){
        repolek.save(new Lek("Ibuprofen", 1500, "200mg", "Lek przeciwbólowy i przeciwzapalny", 12, 99, true, RodzajLeku.PRZECIWBÓLOWE, MarkaLeku.IBUPROM, 20, "foto1.png","500mg wody 500mg kwasu","raz dziennie"));
        repolek.save(new Lek("Paracetamol", 1200, "500mg", "Lek przeciwgorączkowy", 6, 99, true, RodzajLeku.PRZECIWGORĄCZKOWE, MarkaLeku.APAP, 15, "foto2.png","Paracetamol 500mg","co 6 godzin"));
        repolek.save(new Lek("Nurofen Forte", 2000, "400mg", "Silny lek przeciwbólowy", 12, 99, true, RodzajLeku.PRZECIWBÓLOWE, MarkaLeku.NUROFEN, 30, "foto3.png","Ibuprofen 400mg","co 8 godzin"));
        repolek.save(new Lek("Pyralgina", 1800, "500mg", "Silne działanie przeciwbólowe", 12, 99, false, RodzajLeku.PRZECIWBÓLOWE, MarkaLeku.PYRALGINA, 25, "foto4.png","Metamizol sodowy 500mg","co 6 godzin"));
        repolek.save(new Lek("Theraflu Extra", 2500, "4 saszetki", "Na objawy grypy i przeziębienia", 12, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.THERAFLU, 18, "/img/foto5.png","Paracetamol 650mg, Fenylefryna 10mg","2 saszetki dziennie"));
        repolek.save(new Lek("Gripex Max", 2300, "8 tabletek", "Lek na objawy przeziębienia", 12, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.GRIPEX, 22, "foto6.png","Paracetamol 500mg, Kofeina 25mg","co 6 godzin"));
        repolek.save(new Lek("ACC 200", 1700, "2 saszetki", "Lek na kaszel mokry", 6, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.ACC, 12, "foto7.png","Acetylocysteina 200mg","2 razy dziennie"));
        repolek.save(new Lek("Pulneo", 1900, "100ml", "Lek na kaszel suchy", 3, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.PULNEO, 14, "foto8.png","Butamirat 4mg/ml","3 razy dziennie"));
        repolek.save(new Lek("Zyrtec", 2200, "12 tabletek", "Lek na alergię", 6, 99, true, RodzajLeku.NA_ALERGIE, MarkaLeku.ZYRTEC, 10, "foto9.png","Cetyryzyna 10mg","raz dziennie"));
        repolek.save(new Lek("Claritin", 2100, "tabletki", "Lek przeciwhistaminowy", 6, 99, false, RodzajLeku.PRZECIWHISTAMINOWE, MarkaLeku.CLARITINE, 8, "foto10.png","Loratadyna 10mg","raz dziennie"));
        repolek.save(new Lek("Augmentin", 3500, "tabletki", "Antybiotyk na infekcje bakteryjne", 12, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.AUGMENTIN, 5, "foto11.png","Amoksycylina 875mg, Kwas klawulanowy 125mg","2 razy dziennie"));
        repolek.save(new Lek("Duomox", 3300, "tabletki", "Antybiotyk penicylinowy", 12, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.DUOMOX, 6, "foto12.png","Amoksycylina 500mg","3 razy dziennie"));
        repolek.save(new Lek("Metronidazol", 2900, "tabletki", "Lek przeciwbakteryjny i przeciwpierwotniakowy", 18, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.METRONIDAZOL, 4, "foto13.png","Metronidazol 250mg","3 razy dziennie"));
        repolek.save(new Lek("Flegamina", 1600, "syrop", "Na kaszel mokry", 3, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.FLEGAMINA, 11, "foto14.png","Bromheksyna 4mg/5ml","3 razy dziennie"));
        repolek.save(new Lek("Strepsils", 1400, "pastylki", "Na ból gardła", 6, 99, true, RodzajLeku.NA_GARDŁO, MarkaLeku.VICKS_MEDINAIT, 19, "foto15.png","Amylometakrezol 0,6mg, Alkohol dichlorobenzylowy 1,2mg","co 2-3 godziny"));
        repolek.save(new Lek("Tantum Verde", 1800, "spray", "Lek przeciwzapalny na gardło", 6, 99, true, RodzajLeku.NA_GARDŁO, MarkaLeku.COLDREX, 20, "foto16.png","Benzydamina 1,5mg/ml","3-4 razy dziennie"));


    }


    /*private void apteki(){
        repoApteka.save(new Apteka("Apteka Słoneczna", "ul. Legionów 44 m2", "87-800 Włocławek", "894291002", Wojewodztwo.KUJAWSKO_POMORSKIE));
        repoApteka.save(new Apteka("Apteka Zdrowie", "ul. Główna 10", "00-001 Warszawa", "512345678", Wojewodztwo.MAZOWIECKIE));
        repoApteka.save(new Apteka("Apteka Centrum", "ul. Krakowska 15", "31-000 Kraków", "789654123", Wojewodztwo.MALOPOLSKIE));
        repoApteka.save(new Apteka("Apteka Nova", "ul. Dworcowa 7", "40-001 Katowice", "601234567", Wojewodztwo.SLASKIE));
        repoApteka.save(new Apteka("Apteka Przyjazna", "ul. Wojska Polskiego 22", "10-001 Olsztyn", "512987654", Wojewodztwo.WARMINSKO_MAZURSKIE));
        repoApteka.save(new Apteka("Apteka Rodzinna", "ul. Zielona 5", "20-001 Lublin", "789123456", Wojewodztwo.LUBELSKIE));
        repoApteka.save(new Apteka("Apteka Zdrowotna", "ul. Łąkowa 30", "80-001 Gdańsk", "601789123", Wojewodztwo.POMORSKIE));
        repoApteka.save(new Apteka("Apteka Medyczna", "ul. Warszawska 11", "90-001 Łódź", "789456123", Wojewodztwo.LODZKIE));
        repoApteka.save(new Apteka("Apteka Tęczowa", "ul. Słoneczna 21", "70-001 Szczecin", "502345678", Wojewodztwo.ZACHODNIOPOMORSKIE));
        repoApteka.save(new Apteka("Apteka Komfort", "ul. Mickiewicza 9", "50-001 Wrocław", "509876543", Wojewodztwo.DOLNOSLASKIE));
        repoApteka.save(new Apteka("Apteka Vita", "ul. Nowa 18", "85-001 Bydgoszcz", "602345678", Wojewodztwo.KUJAWSKO_POMORSKIE));
        repoApteka.save(new Apteka("Apteka Zdrówko", "ul. Północna 2", "60-001 Poznań", "604567890", Wojewodztwo.WIELKOPOLSKIE));
        repoApteka.save(new Apteka("Apteka Cicha", "ul. Cisowa 13", "35-001 Rzeszów", "603789456", Wojewodztwo.PODKARPACKIE));
        repoApteka.save(new Apteka("Apteka Natury", "ul. Różana 8", "15-001 Białystok", "605432198", Wojewodztwo.PODLASKIE));
        repoApteka.save(new Apteka("Apteka Express", "ul. Kolejowa 3", "26-001 Kielce", "606543210", Wojewodztwo.SWIETOKRZYSKIE));
        repoApteka.save(new Apteka("Apteka Leko", "ul. Królewska 14", "45-001 Opole", "607654321", Wojewodztwo.OPOLSKIE));
        repoApteka.save(new Apteka("Apteka Dobra", "ul. Spokojna 6", "65-001 Zielona Góra", "608765432", Wojewodztwo.LUBUSKIE));
        repoApteka.save(new Apteka("Apteka Harmonia", "ul. Szeroka 22", "32-001 Wieliczka", "609876543", Wojewodztwo.MALOPOLSKIE));
        repoApteka.save(new Apteka("Apteka Pro Zdrowie", "ul. Wysoka 5", "34-001 Zakopane", "610987654", Wojewodztwo.MALOPOLSKIE));
        repoApteka.save(new Apteka("Apteka Serce", "ul. Kasztanowa 12", "22-001 Chełm", "611098765", Wojewodztwo.LUBELSKIE));
        repoApteka.save(new Apteka("Apteka Przyszłości", "ul. Kopernika 9", "01-001 Toruń", "612109876", Wojewodztwo.KUJAWSKO_POMORSKIE));
        repoApteka.save(new Apteka("Apteka Esencja", "ul. Świętojańska 8", "11-001 Giżycko", "613210987", Wojewodztwo.WARMINSKO_MAZURSKIE));
        repoApteka.save(new Apteka("Apteka Modern", "ul. Piłsudskiego 4", "41-001 Sosnowiec", "614321098", Wojewodztwo.SLASKIE));
        repoApteka.save(new Apteka("Apteka Zdrowe Życie", "ul. Lipowa 16", "90-002 Łódź", "615432109", Wojewodztwo.LODZKIE));
        repoApteka.save(new Apteka("Apteka Komfortowa", "ul. Staromiejska 17", "50-002 Wrocław", "616543210", Wojewodztwo.DOLNOSLASKIE));
        repoApteka.save(new Apteka("Apteka Pomocna", "ul. Wesoła 19", "80-002 Gdańsk", "617654321", Wojewodztwo.POMORSKIE));
        repoApteka.save(new Apteka("Apteka Lekarstwa", "ul. Żytnia 20", "65-002 Zielona Góra", "618765432", Wojewodztwo.LUBUSKIE));
        repoApteka.save(new Apteka("Apteka Regionalna", "ul. Orla 21", "22-002 Zamość", "619876543", Wojewodztwo.LUBELSKIE));
        repoApteka.save(new Apteka("Apteka Zdrowa", "ul. Cicha 22", "34-002 Zakopane", "620987654", Wojewodztwo.MALOPOLSKIE));
        repoApteka.save(new Apteka("Apteka Oaza Zdrowia", "ul. Stroma 23", "11-002 Olsztyn", "621098765", Wojewodztwo.WARMINSKO_MAZURSKIE));
        repoApteka.save(new Apteka("Apteka Na Rynku", "ul. Górna 24", "70-002 Szczecin", "622109876", Wojewodztwo.ZACHODNIOPOMORSKIE));
        repoApteka.save(new Apteka("Apteka Wschodnia", "ul. Południowa 25", "32-002 Wieliczka", "623210987", Wojewodztwo.MALOPOLSKIE));
        repoApteka.save(new Apteka("Apteka Perła", "ul. Spacerowa 26", "40-002 Katowice", "624321098", Wojewodztwo.SLASKIE));
        repoApteka.save(new Apteka("Apteka Blisko Ciebie", "ul. Piaskowa 27", "35-002 Rzeszów", "625432109", Wojewodztwo.PODKARPACKIE));
        repoApteka.save(new Apteka("Apteka Zdrówko Małe", "ul. Wspólna 28", "15-002 Białystok", "626543210", Wojewodztwo.PODLASKIE));
        repoApteka.save(new Apteka("Apteka Zachód", "ul. Polna 29", "26-002 Kielce", "627654321", Wojewodztwo.SWIETOKRZYSKIE));
        repoApteka.save(new Apteka("Apteka Przyszłość Zdrowia", "ul. Kwiatowa 30", "45-002 Opole", "628765432", Wojewodztwo.OPOLSKIE));
        repoApteka.save(new Apteka("Apteka Złote Lata", "ul. Leśna 31", "20-002 Lublin", "629876543", Wojewodztwo.LUBELSKIE));
        repoApteka.save(new Apteka("Apteka Zaufanie", "ul. Przylesie 32", "60-002 Poznań", "630987654", Wojewodztwo.WIELKOPOLSKIE));

    }*/







}
