package com.example.otomoto;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("Neuca")
@Controller
public class KontrolerError {

    @RequestMapping("brakDostepu")
    public String brakDostepu(){
        return "brakDostepu";
    }
}
