package com.example.otomoto;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;


public class LekSpecification implements Specification<Lek> {

    private final String wzorzec;
    private final Integer minPrize;
    private final Integer maxPrize;
    private final MarkaLeku markaLeku;
    private final RodzajLeku rodzajLeku;
    private final boolean oferta;

    public LekSpecification(String wzorzec, Integer minPrize, Integer maxPrize, MarkaLeku markaLeku, RodzajLeku rodzajLeku, boolean oferta) {
        this.wzorzec = wzorzec;
        this.minPrize = minPrize;
        this.maxPrize = maxPrize;
        this.markaLeku = markaLeku;
        this.rodzajLeku = rodzajLeku;
        this.oferta=oferta;
    }
    @Override
    public Predicate toPredicate(Root<Lek> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (wzorzec != null && !wzorzec.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + wzorzec + "%"));
        }
        if (minPrize >= 0 && maxPrize > 0) {
            predicates.add(criteriaBuilder.between(root.get("priceGR"), minPrize, maxPrize));
        }
        if (markaLeku != null && markaLeku != MarkaLeku.WSZYSTKIE) {
            predicates.add(criteriaBuilder.equal(root.get("markaLeku"), markaLeku));
        }
        if (rodzajLeku != null && rodzajLeku != RodzajLeku.WSZYSTKIE) {
            predicates.add(criteriaBuilder.equal(root.get("rodzajLeku"), rodzajLeku));
        }
        if (Boolean.TRUE.equals(oferta)) {
            predicates.add(criteriaBuilder.isTrue(root.get("oferta")));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
