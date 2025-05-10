package com.example.otomoto;


import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/Neuca")
@Controller
public class KontrolerApteka {


    @RequestMapping("/strefaAptekarza")
    public String strefaAptekarza(){
        return "strefaAptekarza";
    }
}
