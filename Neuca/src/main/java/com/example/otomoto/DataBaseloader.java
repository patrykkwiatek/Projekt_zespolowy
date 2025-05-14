package com.example.otomoto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataBaseloader implements CommandLineRunner {
    private final RepoLek repolek;
    private final RepoMyUser repoMyUser;
    private final RepoApteka repoApteka;
    private final RepoUzytkownik repoUzytkownik;
    private final PasswordEncoder passwordEncoder;
    private final RepoLekarz repoLekarz;
    private  final RepoWizyta repoWizyta;
    SerwisLekarz serwisLekarz;
    SerwisMyUser serwisMyUser;
    SerwisApteka serwisApteka;
    SerwisPracownik serwisPracownik;
    SerwisKoszyk serwisKoszyk;


    public DataBaseloader(SerwisKoszyk serwisKoszyk, SerwisPracownik serwisPracownik, SerwisApteka serwisApteka, SerwisMyUser serwisMyUser, SerwisLekarz serwisLekarz, RepoLek repolek, RepoMyUser repoMyUser, RepoUzytkownik repoUzytkownik, PasswordEncoder passwordEncoder,RepoApteka repoApteka, RepoLekarz repoLekarz, RepoWizyta repoWizyta) {
        this.repolek = repolek;
        this.repoMyUser = repoMyUser;
        this.repoUzytkownik = repoUzytkownik;
        this.passwordEncoder = passwordEncoder;
        this.repoApteka=repoApteka;
        this.repoLekarz=repoLekarz;
        this.serwisLekarz=serwisLekarz;
        this.serwisMyUser=serwisMyUser;
        this.repoWizyta=repoWizyta;
        this.serwisApteka=serwisApteka;
        this.serwisPracownik=serwisPracownik;
        this.serwisKoszyk=serwisKoszyk;

    }

    @Override
    public void run(String... args) throws Exception {
        leki();

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




        MyUser aptekarz= new MyUser();
        aptekarz.setUsername("aptekarz");
        aptekarz.setPassword(passwordEncoder.encode("aptekarz"));
        aptekarz.setRoles(new ArrayList<>());
        aptekarz.getRoles().add("ROLE_APTEKARZ");

        Apteka apteka= new Apteka();
        apteka.setName("DOZ zdowie");
        apteka.setPotwierdzenie(true);
        apteka.setMyUser(aptekarz);
        apteka.setUlica("Aleja Jana Pawła II");
        apteka.setNumerBud("24");
        apteka.setKodPocztowy("87-800");
        apteka.setMiasto("Włocławek");
        apteka.setTelefon("794291002");
        apteka.setWojewodztwo(Wojewodztwo.KUJAWSKO_POMORSKIE);
        apteka.setPotwierdzenie(true);
        apteka=serwisApteka.zamknijApteke(apteka);
        repoApteka.save(apteka);
        aptekarz.setApteka(apteka);
        repoMyUser.save(aptekarz);








        MyUser user1=new MyUser();
        user1.setUsername("patrykK");
        user1.setPassword(passwordEncoder.encode("haslo"));
        user1.setRoles(new ArrayList<>());
        user1.getRoles().add("ROLE_PACJENT");
        repoMyUser.save(user1);
        MyUser pracownik=new MyUser();
        pracownik.setUsername("pracownik");
        pracownik.setPassword(passwordEncoder.encode("pracownik"));
        pracownik.setRoles(new ArrayList<>());
        pracownik.getRoles().add("ROLE_PRACOWNIK");
        repoMyUser.save(pracownik);



        MyUser lekarz=new MyUser();
        lekarz.setUsername("lekarz");
        lekarz.setEmail("patryk@wp.pl");
        lekarz.setPhone(789234543);
        lekarz.setPlec(true);
        lekarz.setPassword(passwordEncoder.encode("lekarz"));
        lekarz.setRoles(new ArrayList<>());
        lekarz.getRoles().add("ROLE_LEKARZ");

        Lekarz gabinet=new Lekarz();
        gabinet.setPotwierdzenie(false);
        gabinet.setImie("Jan");
        gabinet.setNazwaGabinetu("zdrowie sp zoo");
        gabinet.setNazwisko("NicolaTesla");
        gabinet.setDataUrodzenia(LocalDate.of(1982,2,4));
        gabinet.setAdres1Gabinetu("aleja niepodlegosci 456/45");
        gabinet.setAdres2Gabinetu("00-001 Warszawa");
        gabinet.setAdres2Gabinetu("00-001 Warszawa");
        gabinet.setTelefonGabinet(794291002);
        gabinet.setSredniCzasWizyty(20);
        gabinet.setSpec(LekarzSpec.ALERGOLOGIA);
        gabinet.setMyUser(lekarz);
        gabinet.setMiasto("Warszawa");
        gabinet.setCena(250);
        repoLekarz.save(gabinet);
        repoMyUser.save(lekarz);
        serwisLekarz.dodajLekarza(gabinet,lekarz);
        lekarze();
        pacjenci();
        wizyty();
        aptekiNiePotwierdzone();
        zamowienia();





    }




    private void leki(){
        repolek.save(new Lek("Ibuprofen", 1500, "200mg", "Lek przeciwbólowy i przeciwzapalny", 12, 99, true, RodzajLeku.PRZECIWBÓLOWE, MarkaLeku.IBUPROM, 20, "foto1.png","500mg wody 500mg kwasu","raz dziennie"));
        repolek.save(new Lek("Paracetamol", 1200, "500mg", "Lek przeciwgorączkowy", 6, 99, true, RodzajLeku.PRZECIWGORĄCZKOWE, MarkaLeku.APAP, 15, "foto2.png","Paracetamol 500mg","co 6 godzin"));
        repolek.save(new Lek("Nurofen Forte", 2000, "400mg", "Silny lek przeciwbólowy", 12, 99, true, RodzajLeku.PRZECIWBÓLOWE, MarkaLeku.NUROFEN, 30, "foto3.png","Ibuprofen 400mg","co 8 godzin"));
        repolek.save(new Lek("Pyralgina", 1800, "500mg", "Silne działanie przeciwbólowe", 12, 99, false, RodzajLeku.PRZECIWBÓLOWE, MarkaLeku.PYRALGINA, 25, "foto4.png","Metamizol sodowy 500mg","co 6 godzin"));
        repolek.save(new Lek("Theraflu Extra", 2500, "4 saszetki", "Na objawy grypy i przeziębienia", 12, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.THERAFLU, 18, "foto5.png","Paracetamol 650mg, Fenylefryna 10mg","2 saszetki dziennie"));
        repolek.save(new Lek("Gripex Max", 2300, "8 tabletek", "Lek na objawy przeziębienia", 12, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.GRIPEX, 22, "foto6.png","Paracetamol 500mg, Kofeina 25mg","co 6 godzin"));
        repolek.save(new Lek("ACC 200", 1700, "2 saszetki", "Lek na kaszel mokry", 6, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.ACC, 12, "foto7.png","Acetylocysteina 200mg","2 razy dziennie"));
        repolek.save(new Lek("Pulneo", 1900, "100ml", "Lek na kaszel suchy", 3, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.PULNEO, 14, "foto8.png","Butamirat 4mg/ml","3 razy dziennie"));
        repolek.save(new Lek("Zyrtec", 2200, "12 tabletek", "Lek na alergię", 6, 99, true, RodzajLeku.NA_ALERGIE, MarkaLeku.ZYRTEC, 10, "foto9.png","Cetyryzyna 10mg","raz dziennie"));
        repolek.save(new Lek("Claritin", 2100, "tabletki", "Lek przeciwhistaminowy", 6, 99, true, RodzajLeku.PRZECIWHISTAMINOWE, MarkaLeku.CLARITINE, 8, "foto10.png","Loratadyna 10mg","raz dziennie"));
        repolek.save(new Lek("Augmentin", 3500, "tabletki", "Antybiotyk na infekcje bakteryjne", 12, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.AUGMENTIN, 5, "foto11.png","Amoksycylina 875mg, Kwas klawulanowy 125mg","2 razy dziennie"));
        repolek.save(new Lek("Duomox", 3300, "tabletki", "Antybiotyk penicylinowy", 12, 99, true, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.DUOMOX, 6, "foto12.png","Amoksycylina 500mg","3 razy dziennie"));
        repolek.save(new Lek("Metronidazol", 2900, "tabletki", "Lek przeciwbakteryjny i przeciwpierwotniakowy", 18, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.METRONIDAZOL, 4, "foto13.png","Metronidazol 250mg","3 razy dziennie"));
        repolek.save(new Lek("Flegamina", 1399, "syrop", "Na kaszel mokry", 3, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.FLEGAMINA, 11, "foto14.png","Bromheksyna 4mg/5ml","3 razy dziennie"));
        repolek.save(new Lek("Strepsils", 1360, "pastylki", "Na ból gardła", 6, 99, true, RodzajLeku.NA_GARDŁO, MarkaLeku.VICKS_MEDINAIT, 19, "foto15.png","Amylometakrezol 0,6mg, Alkohol dichlorobenzylowy 1,2mg","co 2-3 godziny"));
        repolek.save(new Lek("Tantum Verde", 1800, "spray", "Lek przeciwzapalny na gardło", 6, 99, true, RodzajLeku.NA_GARDŁO, MarkaLeku.COLDREX, 20, "foto16.png","Benzydamina 1,5mg/ml","3-4 razy dziennie"));
        repolek.save(new Lek("Metafen Max", 1600, "12 tabletek", "Na ból i gorączkę", 12, 99, true, RodzajLeku.PRZECIWBÓLOWE, MarkaLeku.METAFEN, 16, "foto17.png","Ibuprofen 200mg, Paracetamol 325mg","co 6 godzin"));
        repolek.save(new Lek("Panadol Extra", 1700, "500mg", "Lek przeciwbólowy i przeciwgorączkowy", 12, 99, true, RodzajLeku.PRZECIWGORĄCZKOWE, MarkaLeku.PANADOL, 18, "foto18.png","Paracetamol 500mg, Kofeina 65mg","co 8 godzin"));
        repolek.save(new Lek("Fervex Classic", 1800, "saszetki", "Na przeziębienie i grypę", 12, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.FERVEX, 14, "foto19.png","Paracetamol 500mg, Feniramina 25mg","3 razy dziennie"));
        repolek.save(new Lek("Rutinacea", 1200, "20 tabletek", "Wzmacnia odporność", 6, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.RUTINOSCORBIN, 20, "foto20.png","Rutyna 25mg, Witamina C 100mg","raz dziennie"));
        repolek.save(new Lek("Coldrex MaxGrip", 1900, "saszetki", "Na przeziębienie i ból", 12, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.COLDREX, 20, "foto21.png","Paracetamol 1000mg, Kofeina 25mg","2 razy dziennie"));
        repolek.save(new Lek("Scorbolamid", 1500, "20 tabletek", "Na objawy grypy", 12, 99, true, RodzajLeku.NA_PRZEZIĘBIENIE, MarkaLeku.SCORBOLAMID, 22, "foto22.png","Paracetamol 300mg, Rutyna, Witamina C","co 6 godzin"));
        repolek.save(new Lek("Mucosolvan", 1600, "syrop", "Upłynnia wydzielinę", 6, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.MUCOSOLVAN, 15, "foto23.png","Ambroksol 30mg/5ml","2 razy dziennie"));
        repolek.save(new Lek("Ambrosol", 1400, "syrop", "Na kaszel mokry", 6, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.AMBROSOL, 13, "foto24.png","Ambroksol 15mg/5ml","3 razy dziennie"));
        repolek.save(new Lek("Herbapect", 1200, "syrop", "Łagodzi kaszel", 3, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.HERBAPECT, 12, "foto25.png","Wyciąg z tymianku i pierwiosnka","3 razy dziennie"));
        repolek.save(new Lek("Drosetux", 1250, "syrop", "Na kaszel suchy", 3, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.DROSETUX, 11, "foto26.png","Wyciągi roślinne","3 razy dziennie"));
        repolek.save(new Lek("Allegra", 2100, "10 tabletek", "Lek przeciwhistaminowy", 6, 99, true, RodzajLeku.PRZECIWHISTAMINOWE, MarkaLeku.ALLEGRA, 10, "foto27.png","Feksofenadyna 120mg","raz dziennie"));
        repolek.save(new Lek("Aerius", 2000, "tabletki", "Na alergie sezonowe", 6, 99, true, RodzajLeku.NA_ALERGIE, MarkaLeku.AERIUS, 10, "foto28.png","Desloratadyna 5mg","raz dziennie"));
        repolek.save(new Lek("Allertec", 1950, "10 tabletek", "Na alergię", 6, 99, true, RodzajLeku.NA_ALERGIE, MarkaLeku.ALLERTEC, 9, "foto29.png","Cetyryzyna 10mg","raz dziennie"));
        repolek.save(new Lek("Hitaxa Fast", 1850, "tabletki", "Szybkie działanie przeciwalergiczne", 6, 99, true, RodzajLeku.NA_ALERGIE, MarkaLeku.HITAXA, 8, "foto30.png","Bilastyna 20mg","raz dziennie"));
        repolek.save(new Lek("Amotaks", 3400, "tabletki", "Antybiotyk penicylinowy", 12, 99, true, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.AMOTAKS, 6, "foto31.png","Amoksycylina 500mg","3 razy dziennie"));
        repolek.save(new Lek("Tribiotic", 1500, "maść", "Na infekcje skórne", 12, 99, true, RodzajLeku.DERMATOLOGICZNE, MarkaLeku.TRIBIOTIC, 5, "foto32.png","Trzy antybiotyki","raz dziennie"));
        repolek.save(new Lek("Lamisil", 2800, "krem", "Na grzybicę skóry", 12, 99, true, RodzajLeku.PRZECIWGRZYBICZE, MarkaLeku.WSZYSTKIE, 5, "foto33.png","Terbinafina 1%","2 razy dziennie"));
        repolek.save(new Lek("Mycosyst", 2900, "150 mg", "Lek przeciwgrzybiczy", 12, 99, true, RodzajLeku.PRZECIWGRZYBICZE, MarkaLeku.WSZYSTKIE, 4, "foto34.png","Flukonazol 150mg","raz w tygodniu"));
        repolek.save(new Lek("Ketokonazol", 2750, "krem", "Na zakażenia grzybicze", 12, 99, true, RodzajLeku.PRZECIWGRZYBICZE, MarkaLeku.WSZYSTKIE, 5, "foto35.png","Ketokonazol 2%","2 razy dziennie"));
        repolek.save(new Lek("Hydrocortisonum", 1450, "maść", "Na skórne stany zapalne", 12, 99, true, RodzajLeku.DERMATOLOGICZNE, MarkaLeku.WSZYSTKIE, 7, "foto36.png","Hydrokortyzon 1%","2 razy dziennie"));



    }

    private void lekarze(){
        List<String> imiona = Arrays.asList("Anna", "Marek", "Katarzyna", "Jan", "Zofia", "Tomasz", "Barbara", "Paweł", "Agnieszka", "Piotr","Tomasz", "Roman", "Justyna","Bartosz");
        List<String> nazwiska = Arrays.asList("Nowak", "Kowalski", "Wiśniewski", "Wójcik", "Kamińska", "Kaczmarek", "Zieliński", "Szymański", "Woźniak", "Dąbrowski", "Byk", "Lewanswoska");
        List<String> miasta = Arrays.asList("Warszawa", "Kraków", "Łódź", "Wrocław", "Poznań", "Gdańsk", "Szczecin", "Bydgoszcz", "Lublin", "Katowice", "Toruń", "Włocławek", "Gdynia", "Płock", "Gliwice");
        List<LekarzSpec> specjalizacje = Arrays.asList(LekarzSpec.values());

        Random random = new Random();

        for (int i = 1; i <= 50; i++) {
            String imie = imiona.get(random.nextInt(imiona.size()));
            String nazwisko = nazwiska.get(random.nextInt(nazwiska.size()));
            String miasto = miasta.get(random.nextInt(miasta.size()));
            String nazwaGabinetu = nazwisko + "Med" ;

            MyUser user = new MyUser();
            user.setUsername("lekarz" + i);
            user.setEmail("lekarz" + i + "@example.com");
            user.setPhone(600000000 + random.nextInt(99999999));
            user.setPlec(random.nextBoolean());
            user.setPassword(passwordEncoder.encode("haslo"));
            user.setRoles(new ArrayList<>());
            user.getRoles().add("ROLE_LEKARZ");

            Lekarz lekarz = new Lekarz();
            lekarz.setPotwierdzenie(true);
            lekarz.setImie(imie);
            lekarz.setNazwisko(nazwisko);
            lekarz.setNazwaGabinetu(nazwaGabinetu);
            lekarz.setDataUrodzenia(LocalDate.of(
                    1960 + random.nextInt(30),
                    1 + random.nextInt(12),
                    1 + random.nextInt(28)
            ));
            lekarz.setAdres1Gabinetu("ul. Zdrowa " + (10 + random.nextInt(90)));
            lekarz.setAdres2Gabinetu("00-" + String.format("%03d", i) + " " + miasto);
            lekarz.setMiasto(miasto);
            lekarz.setTelefonGabinet(690000000 + random.nextInt(99999999));
            lekarz.setSredniCzasWizyty(10 + random.nextInt(35));
            lekarz.setSpec(specjalizacje.get(random.nextInt(specjalizacje.size())));
            lekarz.setCena(100 + random.nextInt(31) * 10);
            lekarz.setMyUser(user);
            if(i>=1 && i<=27){
                lekarz.setFoto("lekarz"+i+".jpg");
            }
            lekarz.setWizyty(new ArrayList<>());

            repoLekarz.save(lekarz);
            repoMyUser.save(user);
            if(i>5){
                serwisLekarz.dodajLekarza(lekarz, user);
            }else{
                serwisLekarz.dodajLekarzaNie(lekarz,user);
            }

        }
        System.out.println("lekarze");

    }

    void pacjenci(){
        List<String> imiona = Arrays.asList("Anna", "Marek", "Katarzyna", "Jan", "Zofia", "Tomasz", "Barbara", "Paweł", "Agnieszka", "Piotr","Tomasz", "Roman", "Justyna","Bartosz");
        List<String> nazwiska = Arrays.asList("Nowak", "Byk", "Jeleń", "Kot", "Kowal", "Kubiak", "Kwiat", "Kozioł", "Mazur", "Krawczyk", "Kaczmarek", "Zając", "Król", "Wieczorek", "Wróbel", "Sikora","Kawa");
        List<String> miasta = Arrays.asList("Warszawa", "Kraków", "Łódź", "Wrocław", "Poznań", "Gdańsk", "Szczecin", "Bydgoszcz", "Lublin", "Katowice", "Toruń", "Włocławek", "Gdynia", "Płock", "Gliwice");
        for(int i = 1; i<40;i++){
            Random random= new Random();
            MyUser myUser= new MyUser();
            myUser.setImie(imiona.get(random.nextInt(imiona.size())));
            myUser.setNazwisko(nazwiska.get(random.nextInt(nazwiska.size())));
            myUser.setUsername("myuser" + i);
            myUser.setEmail("myuser" + i + "@example.com");
            myUser.setPassword(passwordEncoder.encode("haslo" ));
            myUser.setRoles(new ArrayList<>());
            myUser.getRoles().add("ROLE_PACJENT");
            myUser.setWizyty(new ArrayList<>());
            repoMyUser.save(myUser);
        }
        System.out.println("pacjenci");
    }
    void wizyty() {
        Random random = new Random();
        List<StatusWizyty> statusy = Arrays.asList(StatusWizyty.values());


        MyUser myUser = serwisMyUser.zwrocUser("lekarz");
        if (myUser == null || myUser.getLekarz() == null) {
            System.out.println("Nie znaleziono lekarza.");
            return;
        }

        Lekarz lekarz = myUser.getLekarz();
        System.out.println(lekarz.getImie());

        for (int i = 1; i < 25; i++) {
            MyUser user = serwisMyUser.zwrocUser("myuser" + i);
            if (user == null) {
                System.out.println("Nie znaleziono użytkownika: myuser" + i);
                continue;
            }

            Wizyta wizyta = new Wizyta();
            wizyta.setLekarz(lekarz);
            wizyta.setMyUser(user);
            wizyta.setStatusWizyty(statusy.get(random.nextInt(statusy.size())));
            wizyta.setOpis("opis " + i);

            long minDay = LocalDateTime.now().minusDays(30).toEpochSecond(java.time.ZoneOffset.UTC);
            long maxDay = LocalDateTime.now().plusDays(30).toEpochSecond(java.time.ZoneOffset.UTC);
            long randomEpochSecond = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDateTime randomDate = LocalDateTime.ofEpochSecond(randomEpochSecond, 0, java.time.ZoneOffset.UTC);
            wizyta.setData(randomDate);

            repoWizyta.save(wizyta);



        }
        System.out.println("wizyty");
    }


    void aptekiNiePotwierdzone(){
        Apteka apteka1=new Apteka(false, "Apteka Zdrowie", "Słoneczna", "12", "1", "00-001", "Warszawa", "123456789", Wojewodztwo.MAZOWIECKIE);
        Apteka apteka2=new Apteka(false, "Apteka Centrum", "Główna", "45", "2", "30-002", "Kraków", "987654321", Wojewodztwo.MALOPOLSKIE);
        Apteka apteka3= new Apteka(false, "Apteka Nova", "Leśna", "3", "5", "41-200", "Sosnowiec", "111222333", Wojewodztwo.SLASKIE);
        Apteka apteka4=new Apteka(false, "Apteka Prima", "Lipowa", "9", "8", "60-123", "Poznań", "444555666", Wojewodztwo.WIELKOPOLSKIE);
        Apteka apteka5=new Apteka(false, "Apteka Vita", "Brzozowa", "7A", "", "80-100", "Gdańsk", "555666777", Wojewodztwo.POMORSKIE);

        MyUser myUser1=new MyUser(List.of("ROLE_APTEKARZ"), "haslo", true, 500111222, "adam.kowalski@example.com", "adamk", "Kowalski", "Adam");
        MyUser myUser2=new MyUser(List.of("ROLE_APTEKARZ"), "haslo", false, 501222333, "ewa.nowak@example.com", "ewan", "Nowak", "Ewa");
        MyUser myUser3=new MyUser(List.of("ROLE_APTEKARZ"), "haslo", true, 502333444, "jan.zielinski@example.com", "janz", "Zieliński", "Jan");
        MyUser myUser4=new MyUser(List.of("ROLE_APTEKARZ"), "haslo", false, 503444555, "magda.wojciechowska@example.com", "magdaw", "Wojciechowska", "Magda");
        MyUser myUser5=new MyUser(List.of("ROLE_APTEKARZ"), "haslo", true, 504555666, "piotr.lewandowski@example.com", "piotrl", "Lewandowski", "Piotr");
        serwisApteka.dodajApteke(myUser1,apteka1);
        serwisApteka.dodajApteke(myUser2,apteka2);
        serwisApteka.dodajApteke(myUser3,apteka3);
        serwisApteka.dodajApteke(myUser4,apteka4);
        serwisApteka.dodajApteke(myUser5,apteka5);
    }


    void zamowienia(){
        //zamowienie 1
        MyUser myUser=serwisMyUser.zwrocUser("patrykP");
        ProduktKoszyk p1=new ProduktKoszyk();
        serwisKoszyk.dodajDoKoszyka(myUser,serwisPracownik.findbyID(Long.valueOf(3)),1);
        serwisKoszyk.dodajDoKoszyka(myUser,serwisPracownik.findbyID(Long.valueOf(4)),8);
        Zamowienie zamowienie=serwisKoszyk.getAktywnyKoszyk(myUser);

        zamowienie.setImie("Patryk");
        zamowienie.setNazwisko("Kwiatkowski");
        zamowienie.setEmail("patryk@wp.pl");
        zamowienie.setTelefon(600983654);
        zamowienie.setAdres1("Aleja Jana Pawla II 24");
        zamowienie.setAdres2("87-800 Włocławek");
        zamowienie.setStatus(Status.OCZEKUJE);
        zamowienie.setCzyZakonczone(true);
        zamowienie.setDostawa(Dostawa.POCZTA);
        zamowienie.setDataZamowienia(LocalDateTime.now());
        LocalDateTime data= LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        zamowienie.setDisplayDateZamowienia(data.format(formatter));
        zamowienie.setFaktura(new Faktura(false));
        int sumaKoszyka=0;
        for (ProduktKoszyk produkt : zamowienie.getProduktKoszyk()) {
            long cenaJednostkowaGrosze = produkt.getLek().getPriceGR();
            long cenaCalosciowaGrosze = cenaJednostkowaGrosze * produkt.getIlosc();


            sumaKoszyka += cenaCalosciowaGrosze; // Dodanie ceny całkowitej do sumy koszyka
        }

        zamowienie.setCalkowitaCena(String.format("%.2f", sumaKoszyka / 100.0));
        serwisKoszyk.noweZamowienie(zamowienie,myUser);

        //zamowienie 2

        myUser = serwisMyUser.zwrocUser("myuser4");
        p1 = new ProduktKoszyk();
        serwisKoszyk.dodajDoKoszyka(myUser, serwisPracownik.findbyID(10L), 2);
        serwisKoszyk.dodajDoKoszyka(myUser, serwisPracownik.findbyID(5L), 5);
        serwisKoszyk.dodajDoKoszyka(myUser, serwisPracownik.findbyID(12L), 1);
        zamowienie = serwisKoszyk.getAktywnyKoszyk(myUser);
        zamowienie.setImie("Anna");
        zamowienie.setNazwisko("Zielińska");
        zamowienie.setEmail("anna.zielinska@example.com");
        zamowienie.setTelefon(501123456);
        zamowienie.setAdres1("ul. Słoneczna 5");
        zamowienie.setAdres2("00-123 Warszawa");
        zamowienie.setStatus(Status.losowy());
        zamowienie.setCzyZakonczone(true);
        zamowienie.setDostawa(Dostawa.KURIER);
        zamowienie.setDataZamowienia(LocalDateTime.now());



        zamowienie.setDisplayDateZamowienia(data.format(formatter));
        String dataF=data.format(formatter);

        zamowienie.setFaktura(new Faktura(true,"firma HandlowoUslugowa","aleja niepodleglosci 2","00-001 Warszaw","8885672356",LocalDateTime.now(),dataF));

        sumaKoszyka = 0;
        for (ProduktKoszyk produkt : zamowienie.getProduktKoszyk()) {
            long cenaJednostkowaGrosze = produkt.getLek().getPriceGR(); // <-- upewnij się, że jesteś wewnątrz @Transactional!
            long cenaCalosciowaGrosze = cenaJednostkowaGrosze * produkt.getIlosc();
            sumaKoszyka += cenaCalosciowaGrosze;
        }

        zamowienie.setCalkowitaCena(String.format("%.2f", sumaKoszyka / 100.0));
        serwisKoszyk.noweZamowienie(zamowienie, myUser);



        List<String> imiona = Arrays.asList("Anna", "Bartek", "Celina", "Dawid", "Ewa", "Filip", "Gabriela", "Hubert", "Iwona", "Jakub",
                "Katarzyna", "Łukasz", "Maja", "Norbert", "Oliwia", "Patryk", "Roksana", "Sebastian", "Tomasz",
                "Urszula", "Wojciech", "Zofia", "Marcin", "Natalia", "Zenon");

        List<String> nazwiska = Arrays.asList("Nowak", "Byk", "Jeleń", "Kot", "Kowal", "Kubiak", "Kwiat", "Kozioł", "Mazur", "Krawczyk", "Kaczmarek", "Zając", "Król", "Wieczorek", "Wróbel", "Sikora","Kawa");
        List<String> miasta = Arrays.asList("Warszawa", "Kraków", "Łódź", "Wrocław", "Poznań", "Gdańsk", "Szczecin", "Bydgoszcz", "Lublin", "Katowice", "Toruń", "Włocławek", "Gdynia", "Płock", "Gliwice");



        Random random = new Random();
        for (int i = 1; i < 25; i++) {
            myUser = serwisMyUser.zwrocUser("myuser"+i);
            serwisKoszyk.dodajDoKoszyka(myUser, serwisPracownik.findbyID(4L),random.nextInt(12));
            serwisKoszyk.dodajDoKoszyka(myUser, serwisPracownik.findbyID(6L), random.nextInt(12));
            serwisKoszyk.dodajDoKoszyka(myUser, serwisPracownik.findbyID(1L), random.nextInt(12));

            Zamowienie z1 = serwisKoszyk.getAktywnyKoszyk(myUser);

            z1.setImie(imiona.get(random.nextInt(imiona.size())));
            z1.setNazwisko(nazwiska.get(random.nextInt(nazwiska.size())));
            z1.setEmail(z1.getImie()+z1.getNazwisko()+"@mail.com");
            z1.setTelefon(500100100 + i);
            z1.setAdres1("Ul. Zamówieniowa " + (i + 1));
            String miasto=miasta.get(random.nextInt(miasta.size()));
            z1.setAdres2("00-" + String.format("%03d", 100 + i)+" " + miasto);
            z1.setStatus(Status.losowy());
            z1.setCzyZakonczone(false);
            z1.setDostawa(Dostawa.ODBIOR);
            z1.setDataZamowienia(data);
            z1.setDisplayDateZamowienia(data.format(formatter));
            z1.setFaktura(new Faktura(false));

            long suma = z1.getProduktKoszyk().stream()
                    .mapToLong(p -> p.getLek().getPriceGR() * p.getIlosc())
                    .sum();
            z1.setCalkowitaCena(String.format("%.2f", suma / 100.0));

            serwisKoszyk.noweZamowienie(z1, myUser);
        }

        System.out.println("zamowienia");

    }






}
