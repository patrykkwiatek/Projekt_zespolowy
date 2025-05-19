package com.example.otomoto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RepoWizyta extends JpaRepository<Wizyta, Long>, JpaSpecificationExecutor<Wizyta> {
    Page<Wizyta> findByLekarz(Lekarz lekarz, Pageable pageable);
    Page<Wizyta> findByLekarzAndStatusWizyty(Lekarz lekarz, StatusWizyty status, Pageable pageable);
    List<Wizyta> findByMyUser(MyUser myUser);
}
