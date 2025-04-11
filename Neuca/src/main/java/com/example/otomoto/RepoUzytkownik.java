package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepoUzytkownik extends JpaRepository<Uzytkownik, Long> {


}
