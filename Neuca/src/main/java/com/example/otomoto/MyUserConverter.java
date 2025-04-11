package com.example.otomoto;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUserConverter {
    public MyUser createMyUser(MyUserDTO myUserDTO){
        MyUser myUser = new MyUser();
        myUser.setId(myUserDTO.getId());
        myUser.setImie(myUserDTO.getImie());
        myUser.setNazwisko(myUserDTO.getNazwisko());
        myUser.setUsername(myUserDTO.getUsername());
        myUser.setEmail(myUserDTO.getEmail());
        myUser.setPhone(myUserDTO.getPhone());
        myUser.setPlec(myUserDTO.isPlec());
        myUser.setPassword(myUserDTO.getPassword());
        myUser.setRoles(myUserDTO.getRoles());
        myUser.setLekarzSpec(myUserDTO.getLekarzSpec());
        //myUser.setZamowienie(myUser.getZamowienie());
        return myUser;
    }
}