package com.example.otomoto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class MyDatabaseUserDetailsService implements UserDetailsService {
    private RepoMyUser repoMyUser;

    public MyDatabaseUserDetailsService (RepoMyUser repoMyUser){
        this.repoMyUser=repoMyUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        MyUser user = repoMyUser.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("brak") );


        List<GrantedAuthority> authorities =user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return  new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);

    }
}
