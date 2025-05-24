package com.example.otomoto;


import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SerwisWizyta {
    RepoWizyta repoWizyta;

    public SerwisWizyta(RepoWizyta repoWizyta) {
        this.repoWizyta = repoWizyta;
    }

    Wizyta zwrocWizytaID(Long id){
        return repoWizyta.findById(id).orElse(null);
    }
    void zmienStatus(Long id, StatusWizyty statusWizyty){
        Wizyta wizyta=repoWizyta.findById(id).orElse(null);
        wizyta.setStatusWizyty(statusWizyty);
        repoWizyta.save(wizyta);
    }

    void zapiszWizyte(Wizyta wizyta){
        repoWizyta.save(wizyta);
    }

    void usunWizyte(Wizyta wizyta){
        repoWizyta.delete(wizyta);
    }

    List<Wizyta> zwrocMojeWizyty(MyUser myUser){
        List<Wizyta> lista=repoWizyta.findByMyUser(myUser);
        return  lista;
    }
    void zmienStatus(Wizyta wizyta, StatusWizyty statusWizyty){
        wizyta.setStatusWizyty(statusWizyty);
        repoWizyta.save(wizyta);
    }

    public List<LocalDateTime> zwrocWolneTerminy(Lekarz lekarz){
        List<LocalDateTime> wolneTerminy = new ArrayList<>();
        List<Wizyta> zajeteWizyty = lekarz.getWizyty();
        Map<DayOfWeek, GrafikLekarz> grafikMap = lekarz.getGrafikLekarz().stream()
                .collect(Collectors.toMap(GrafikLekarz::getDzienTygodnia, g -> g));

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(3);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DayOfWeek dzien = date.getDayOfWeek();
            GrafikLekarz grafik = grafikMap.get(dzien);

            if (grafik == null || grafik.isZamkniete() || grafik.getGodziny() == null) continue;

            for (Integer godzina : grafik.getGodziny()) {
                LocalDateTime potencjalnyTermin = LocalDateTime.of(date, LocalTime.of(godzina, 0));

                boolean zajety = zajeteWizyty.stream()
                        .anyMatch(w -> w.getData().equals(potencjalnyTermin));

                if (!zajety) {
                    wolneTerminy.add(potencjalnyTermin);
                }
            }
        }
        return wolneTerminy;


    }

    public void zarezerwujWizyte(MyUser myUser, Lekarz lekarz ,LocalDateTime data){
        Wizyta w =new Wizyta();
        w.setLekarz(lekarz);
        w.setMyUser(myUser);
        w.setStatusWizyty(StatusWizyty.ZAREZERWOWANABRAKPOTW);
        w.setData(data);
        repoWizyta.save(w);

    }

}
