package com.example.otomoto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RepoWizyta extends JpaRepository<Wizyta, Long>, JpaSpecificationExecutor<Wizyta> {
    List<Wizyta> findByLekarz(Lekarz lekarz);
    List<Wizyta> findByLekarzAndStatusWizyty(Lekarz lekarz, StatusWizyty status);
    List<Wizyta> findByMyUser(MyUser myUser);
}
