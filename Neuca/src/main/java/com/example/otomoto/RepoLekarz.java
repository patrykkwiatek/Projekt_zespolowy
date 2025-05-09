package com.example.otomoto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoLekarz extends JpaRepository<Lekarz, Long> {
    List<Lekarz> findByPotwierdzenie(boolean potwierdzenie);
        Page<Lekarz> findBySpecAndMiastoContainingIgnoreCase(LekarzSpec spec, String miasto, Pageable pageable);
        Page<Lekarz> findByMiastoContainingIgnoreCase(String miasto, Pageable pageable);
    Page<Lekarz> findBySpec(LekarzSpec spec, Pageable pageable);




}
