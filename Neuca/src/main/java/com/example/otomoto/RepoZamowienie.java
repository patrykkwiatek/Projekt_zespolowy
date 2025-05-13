package com.example.otomoto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoZamowienie extends JpaRepository<Zamowienie, Long> {
    Optional<Zamowienie> findByMyuserAndCzyZakonczone(MyUser user, boolean czyZakonczone);
    Page<Zamowienie> findByStatus(Status status, Pageable pageable);
    Optional <Zamowienie> findById(Long id);
    List<Zamowienie> findByMyuserAndStatusNot(MyUser user, Status status);
    Page<Zamowienie> findByStatusNot(Status status, Pageable pageable);
}
