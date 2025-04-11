package com.example.otomoto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoApteka extends JpaRepository<Apteka, Long> {

    Page<Apteka> findByNameContainingIgnoreCase(String nazwa, Pageable pageable);

    Page<Apteka> findByWojewodztwo(Wojewodztwo wojewodztwo, Pageable pageable);

    Page<Apteka> findByWojewodztwoAndNameContainingIgnoreCase(Wojewodztwo wojewodztwo, String name, Pageable pageable);
}
