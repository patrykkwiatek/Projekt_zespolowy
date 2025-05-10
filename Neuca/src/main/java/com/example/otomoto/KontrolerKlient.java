package com.example.otomoto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Neuca")
public class KontrolerKlient {
    private SerwisPracownik sP;
    private SerwisKlient sK;

    public KontrolerKlient(SerwisPracownik sP, SerwisKlient sK) {
        this.sP = sP;
        this.sK = sK;
    }

    @RequestMapping("/startK/listaK")
    public String lista(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "Ids") String sort,
                        @RequestParam(defaultValue = "") String wzorzec,
                        Model model) {

        /*List<Lek> filteredContent = wynik.getContent()
                .stream()
                .filter(lek -> lek.isOferta())
                .toList();

        Page<Lek> filteredWynik = new PageImpl<>
                (filteredContent, wynik.getPageable(), filteredContent.size());

        model.addAttribute("lista", filteredWynik);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);
        model.addAttribute("numbers", sP.createPageNumbers(page, filteredWynik.getTotalPages()));
        model.addAttribute("total", filteredWynik.getTotalPages());
        model.addAttribute("pageA", page + 1);
        model.addAttribute("wzorzec", wzorzec);*/
        return "listaK";
    }

    @RequestMapping("/startK/DodajDoKoszyka/{id}")
    public String DodajDoKoszyka(@PathVariable Long id){

        return "DodajDoKoszyka";
    }




    @RequestMapping("/pill")
    public String pill(){
        return "pill";
    }

    @RequestMapping("/buy")
    public String buy(Authentication authentication,Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "Ids") String sort,
                       @RequestParam(defaultValue = "") String wzorzec,
                       @RequestParam(defaultValue = "0") int minPrize,
                       @RequestParam(defaultValue = "1000") int maxPrize,
                       @RequestParam(required = false) MarkaLeku markaLeku,
                       @RequestParam(required = false) RodzajLeku rodzajLeku){
        boolean isLogged = authentication != null && authentication.isAuthenticated();
        if (markaLeku == null) {
            markaLeku = MarkaLeku.WSZYSTKIE;
        }
        if (rodzajLeku == null) {
            rodzajLeku = RodzajLeku.WSZYSTKIE;
        }
        Page<Lek> wyniki = sP.getAllDtoLeki(page, size, sort, wzorzec, minPrize, maxPrize, markaLeku, rodzajLeku,true);
        List<RodzajLeku> rodzaje = Arrays.asList(RodzajLeku.values());
        List<MarkaLeku> marki = Arrays.asList(MarkaLeku.values());

        if(minPrize!=0){
            model.addAttribute("minPrize",minPrize);
        }
        int total=wyniki.getTotalPages();
        boolean czy0;
        if(total==0){
            czy0=true;
        }else{
            czy0=false;
        }


        boolean czyPierwsza;
        if(page==0){
            czyPierwsza=true;
        }else{
            czyPierwsza=false;
        }

        boolean czyOstatnia;
        if(page+1==total){
            czyOstatnia=true;
        }else{
            czyOstatnia=false;
        }
        int nastepna=page+1;
        int poprzednia=page-1;

        model.addAttribute("total",total);
        model.addAttribute("total1",total-1);
        model.addAttribute("page",page+1);
        model.addAttribute("pageN",page);
        model.addAttribute("poprzednia",poprzednia);
        model.addAttribute("nastepna",nastepna);
        model.addAttribute("czyPierwsza",czyPierwsza);
        model.addAttribute("czyOstatnia",czyOstatnia);
        model.addAttribute("czy0",czy0);
        model.addAttribute("wzorzec",wzorzec);
        model.addAttribute("maxPrize", maxPrize);
        model.addAttribute("sort", sort);
        model.addAttribute("rodzajLeku", rodzajLeku);
        model.addAttribute("markaLeku", markaLeku);
        model.addAttribute("rodzaje", rodzaje);
        model.addAttribute("marki", marki);
        model.addAttribute("wyniki", wyniki);
        model.addAttribute("log", isLogged);
        model.addAttribute("size",size);
        return "buy";
    }

}
