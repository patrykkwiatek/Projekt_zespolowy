package com.example.otomoto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepoLek extends JpaRepository<Lek, Long>,  JpaSpecificationExecutor<Lek> {
    Page<Lek> findByNameContainingIgnoreCase(String name, Pageable pageable);

}

