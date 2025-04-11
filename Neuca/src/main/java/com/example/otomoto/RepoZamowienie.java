package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoZamowienie extends JpaRepository<Zamowienie, Long> {
    Optional<Zamowienie> findByMyuserAndCzyZakonczone(MyUser user, boolean czyZakonczone);
    List<Zamowienie> findByStatus(Status status);
    Optional <Zamowienie> findById(Long id);
    List<Zamowienie> findByMyuserAndStatusNot(MyUser user, Status status);

}
