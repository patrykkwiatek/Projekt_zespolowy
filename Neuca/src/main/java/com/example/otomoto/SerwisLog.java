package com.example.otomoto;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerwisLog {
    private final SerwisMyUser serwisMyUser;

    public SerwisLog(SerwisMyUser serwisMyUser) {
        this.serwisMyUser = serwisMyUser;
    }

    public String strefa(Authentication authentication) {
        MyUser myUser = serwisMyUser.zwrocUser(authentication.getName());
        List<String> role = myUser.getRoles();
        String Rola = role.getFirst();

        if (Rola.equals("ROLE_APTEKARZ")) {
            return "/Neuca/strefaAptekarza";
        } else if (Rola.equals("ROLE_LEKARZ")) {
            return "/Neuca/strefaLekarza";
        } else if (Rola.equals("ROLE_PRACOWNIK")) {
            return "/Neuca/strefaPracownika";
        } else {
            return "/Neuca/start";
        }
    }
}
