package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoLekStanApteka extends JpaRepository<LekStanApteka, Long> {
    Optional<LekStanApteka> findByLekAndApteka(Lek lek, Apteka apteka);
    List<LekStanApteka> findByApteka(Apteka apteka);
    Optional<LekStanApteka> findById(Long id);
}
