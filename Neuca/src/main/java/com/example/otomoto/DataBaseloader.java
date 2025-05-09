package com.example.otomoto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
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


    public DataBaseloader(SerwisMyUser serwisMyUser, SerwisLekarz serwisLekarz, RepoLek repolek, RepoMyUser repoMyUser, RepoUzytkownik repoUzytkownik, PasswordEncoder passwordEncoder,RepoApteka repoApteka, RepoLekarz repoLekarz, RepoWizyta repoWizyta) {
        this.repolek = repolek;
        this.repoMyUser = repoMyUser;
        this.repoUzytkownik = repoUzytkownik;
        this.passwordEncoder = passwordEncoder;
        this.repoApteka=repoApteka;
        this.repoLekarz=repoLekarz;
        this.serwisLekarz=serwisLekarz;
        this.serwisMyUser=serwisMyUser;
        this.repoWizyta=repoWizyta;
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
        gabinet.setPotwierdzenie(true);
        gabinet.setImie("Jan");
        gabinet.setNazwaGabinetu("zdrowie sp zoo");
        gabinet.setNazwisko("NicolaTesla");
        gabinet.setDataUrodzenia(LocalDate.of(1982,2,4));
        gabinet.setAdres1Gabinetu("aleja niepodlegosci 456/45");
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
        repolek.save(new Lek("Claritin", 2100, "tabletki", "Lek przeciwhistaminowy", 6, 99, false, RodzajLeku.PRZECIWHISTAMINOWE, MarkaLeku.CLARITINE, 8, "foto10.png","Loratadyna 10mg","raz dziennie"));
        repolek.save(new Lek("Augmentin", 3500, "tabletki", "Antybiotyk na infekcje bakteryjne", 12, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.AUGMENTIN, 5, "foto11.png","Amoksycylina 875mg, Kwas klawulanowy 125mg","2 razy dziennie"));
        repolek.save(new Lek("Duomox", 3300, "tabletki", "Antybiotyk penicylinowy", 12, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.DUOMOX, 6, "foto12.png","Amoksycylina 500mg","3 razy dziennie"));
        repolek.save(new Lek("Metronidazol", 2900, "tabletki", "Lek przeciwbakteryjny i przeciwpierwotniakowy", 18, 99, false, RodzajLeku.PRZECIWBAKTERYJNE, MarkaLeku.METRONIDAZOL, 4, "foto13.png","Metronidazol 250mg","3 razy dziennie"));
        repolek.save(new Lek("Flegamina", 1399, "syrop", "Na kaszel mokry", 3, 99, true, RodzajLeku.NA_KASZEL, MarkaLeku.FLEGAMINA, 11, "foto14.png","Bromheksyna 4mg/5ml","3 razy dziennie"));
        repolek.save(new Lek("Strepsils", 1360, "pastylki", "Na ból gardła", 6, 99, true, RodzajLeku.NA_GARDŁO, MarkaLeku.VICKS_MEDINAIT, 19, "foto15.png","Amylometakrezol 0,6mg, Alkohol dichlorobenzylowy 1,2mg","co 2-3 godziny"));
        repolek.save(new Lek("Tantum Verde", 1800, "spray", "Lek przeciwzapalny na gardło", 6, 99, true, RodzajLeku.NA_GARDŁO, MarkaLeku.COLDREX, 20, "foto16.png","Benzydamina 1,5mg/ml","3-4 razy dziennie"));


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
            user.setPassword(passwordEncoder.encode("haslo" + i));
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
            serwisLekarz.dodajLekarza(lekarz, user);
        }

    }

    void pacjenci(){
        List<String> imiona = Arrays.asList("Anna", "Marek", "Katarzyna", "Jan", "Zofia", "Tomasz", "Barbara", "Paweł", "Agnieszka", "Piotr","Tomasz", "Roman", "Justyna","Bartosz");
        List<String> nazwiska = Arrays.asList("Nowak", "Kowalski", "Wiśniewski", "Wójcik", "Kamińska", "Kaczmarek", "Zieliński", "Szymański", "Woźniak", "Dąbrowski", "Byk", "Lewanswoska");
        List<String> miasta = Arrays.asList("Warszawa", "Kraków", "Łódź", "Wrocław", "Poznań", "Gdańsk", "Szczecin", "Bydgoszcz", "Lublin", "Katowice", "Toruń", "Włocławek", "Gdynia", "Płock", "Gliwice");
        for(int i = 1; i<40;i++){
            Random random= new Random();
            MyUser myUser= new MyUser();
            myUser.setImie(imiona.get(random.nextInt(imiona.size())));
            myUser.setNazwisko(nazwiska.get(random.nextInt(nazwiska.size())));
            myUser.setUsername("myuser" + i);
            myUser.setEmail("myuser" + i + "@example.com");
            myUser.setPassword(passwordEncoder.encode("haslo" + i));
            myUser.setRoles(new ArrayList<>());
            myUser.getRoles().add("ROLE_PACJENT");
            myUser.setWizyty(new ArrayList<>());
            repoMyUser.save(myUser);
        }
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
            System.out.println(user.getImie());

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

            repoWizyta.save(wizyta); // zapisujemy tylko wizytę


        }
    }




}
