package com.example.otomoto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoApteka extends JpaRepository<Apteka, Long> {
    List<Apteka> findByPotwierdzenie(boolean potwierdzenie);

}
