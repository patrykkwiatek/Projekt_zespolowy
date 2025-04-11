package com.example.otomoto;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerwisMyUser {
    private final RepoMyUser repoMyUser;
    private final MyUserConverter myUserConverter;
    private final PasswordEncoder passwordEncoder;
    public SerwisMyUser(RepoMyUser repoMyUser, MyUserConverter myUserConverter, PasswordEncoder passwordEncoder) {
        this.repoMyUser = repoMyUser;
        this.myUserConverter = myUserConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addUserPacjent(MyUserDTO myUserDTO) {
        if (repoMyUser.findByUsername(myUserDTO.getUsername()).isEmpty()) {
            MyUser myUser = myUserConverter.createMyUser(myUserDTO);
            myUser.setPassword(passwordEncoder.encode(myUserDTO.getPassword()));
            if (myUserDTO.getRoles() == null || myUserDTO.getRoles().isEmpty()) {
                List<String> roles = List.of("PACJENT");
                myUser.setRoles(roles);
            } else {
                myUser.setRoles(myUserDTO.getRoles());
            }
            repoMyUser.save(myUser);
            return true;
        }
        return false;
    }
    public boolean addUserAptekarz(MyUserDTO myUserDTO) {
        if (repoMyUser.findByUsername(myUserDTO.getUsername()).isEmpty()) {
            MyUser myUser = myUserConverter.createMyUser(myUserDTO);
            myUser.setPassword(passwordEncoder.encode(myUserDTO.getPassword()));
            if (myUserDTO.getRoles() == null || myUserDTO.getRoles().isEmpty()) {
                List<String> roles = List.of("APTEKARZ");
                myUser.setRoles(roles);
            } else {
                myUser.setRoles(myUserDTO.getRoles());
            }
            repoMyUser.save(myUser);
            return true;
        }
        return false;
    }

    public boolean addUserLekarz(MyUserDTO myUserDTO) {
        if (repoMyUser.findByUsername(myUserDTO.getUsername()).isEmpty()) {
            MyUser myUser = myUserConverter.createMyUser(myUserDTO);
            myUser.setPassword(passwordEncoder.encode(myUserDTO.getPassword()));
            if (myUserDTO.getRoles() == null || myUserDTO.getRoles().isEmpty()) {
                List<String> roles = List.of("LEKARZ");
                myUser.setRoles(roles);
            } else {
                myUser.setRoles(myUserDTO.getRoles());
            }
            repoMyUser.save(myUser);
            return true;
        }
        return false;
    }

    public MyUser zwrocUser(String username){
        return repoMyUser.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik o nazwie " + username + " nie istnieje"));
    }

    public boolean czyMaROle(String rola, MyUser myUser){
        return myUser.getRoles() != null && myUser.getRoles().contains(rola);
    }




}
