package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SerwisGrafik {
    RepoGrafikLekarz repoGrafikLekarz;
    RepoLekarz repoLekarz;
    SerwisGrafik(RepoLekarz repoLekarz, RepoGrafikLekarz repoGrafikLekarz){
        this.repoGrafikLekarz=repoGrafikLekarz;
        this.repoLekarz=repoLekarz;
    }

    void zapiszGrafik(GrafikLekarz grafikLekarz){
        if(grafikLekarz.isZamkniete()==true){
            grafikLekarz.setGodzinaStart(LocalTime.of(0, 0));
            grafikLekarz.setGodzinaKoniec(LocalTime.of(0, 0));
        }
        repoGrafikLekarz.save(grafikLekarz);
    }

    public GrafikLekarz znajdzGrafik(Lekarz lekarz, DayOfWeek dzien){
        GrafikLekarz grafikLekarz=repoGrafikLekarz.findByLekarzAndDzienTygodnia(lekarz,dzien);
        System.out.println(grafikLekarz.getDzienTygodnia()+" start: "+grafikLekarz.getGodzinaStart());
        return grafikLekarz;
    }

    public DayOfWeek zwrocDzien(int numer){
        if(numer==1){
            return DayOfWeek.MONDAY;
        }else if(numer==2){
            return DayOfWeek.TUESDAY;
        }else if(numer==3){
            return DayOfWeek.WEDNESDAY;
        }else if(numer==4){
            return DayOfWeek.THURSDAY;
        }else if(numer==5){
            return DayOfWeek.FRIDAY;
        }else if(numer==6){
            return DayOfWeek.SATURDAY;
        }else if(numer==7){
            return DayOfWeek.SUNDAY;
        }else{
            throw new IllegalArgumentException("Nieprawidłowy numer dnia tygodnia: " + numer);
        }

    }

    public String zwrocDzienString(int numer){
        if(numer==1){
            return "dzien.pon";
        }else if(numer==2){
            return "dzien.wt";
        }else if(numer==3){
            return "dzien.sr";
        }else if(numer==4){
            return "dzien.czw";
        }else if(numer==5){
            return "dzien.pt";
        }else if(numer==6){
            return "dzien.sob";
        }else if(numer==7){
            return "dzien.nd";
        }else{
            throw new IllegalArgumentException("Nieprawidłowy numer dnia tygodnia: " + numer);
        }
    }

    void zmienGrafik(Lekarz lekarz, int dzien, LocalTime start, LocalTime koniec, boolean zamkniete){
        GrafikLekarz grafikLekarz=znajdzGrafik(lekarz,zwrocDzien(dzien));
        grafikLekarz.setGodzinaStart(start);
        grafikLekarz.setGodzinaKoniec(koniec);
        grafikLekarz.setZamkniete(zamkniete);
        grafikLekarz.setGodziny(new ArrayList<>());

        zapiszGrafik(grafikLekarz);
    }

    List<Integer> zwrocGodziny(GrafikLekarz grafikLekarz){
        List<Integer> lista=new ArrayList<>();
        LocalTime start=grafikLekarz.getGodzinaStart();
        LocalTime koniec=grafikLekarz.getGodzinaKoniec();
        int startMin=start.getMinute();
        int startHour=start.getHour();
        int koniecMin=koniec.getMinute();
        int koniecHour=koniec.getHour();
        if(startMin==0 && startHour !=koniecHour){
            lista.add(startHour);
        }

        for(int i=startHour+1;i<koniecHour;i++){
            lista.add(i);
        }

        if(koniecMin !=0 && startHour !=koniecHour){
            lista.add(koniecHour);
        }
        return lista;
    }


    public void usunGrafik(GrafikLekarz grafikLekarz){
        repoGrafikLekarz.delete(grafikLekarz);
    }
}
