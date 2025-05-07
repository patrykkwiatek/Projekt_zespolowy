package com.example.otomoto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class WizytaSpecification implements Specification<Wizyta> {
    private final String czyOdbyte;
    private final StatusWizyty statusWizyty;
    private final Lekarz lekarz;

    public WizytaSpecification(String czyOdbyte, StatusWizyty statusWizyty, Lekarz lekarz) {
        this.czyOdbyte = czyOdbyte;
        this.statusWizyty = statusWizyty;
        this.lekarz=lekarz;
    }
    @Override
    public Predicate toPredicate(Root<Wizyta> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
        List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
        LocalDateTime teraz = LocalDateTime.now();


        if (!"all".equalsIgnoreCase(czyOdbyte)) {
            if ("tak".equalsIgnoreCase(czyOdbyte)) {
                // Odbyte = data w przeszłości
                predicates.add(criteriaBuilder.lessThan(root.get("data"), teraz));
            } else if ("nie".equalsIgnoreCase(czyOdbyte)) {
                // Nieodbyte = data w przyszłości
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("data"), teraz));
            }
        }
        if (statusWizyty != null) {
            predicates.add(criteriaBuilder.equal(root.get("statusWizyty"), statusWizyty));
        }

        if (lekarz != null) {
            predicates.add(criteriaBuilder.equal(root.get("lekarz"), lekarz));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }
}
