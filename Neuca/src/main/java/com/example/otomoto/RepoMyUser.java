package com.example.otomoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoMyUser extends JpaRepository <MyUser, Long> {
    Optional<MyUser> findByUsername(String login);
}
