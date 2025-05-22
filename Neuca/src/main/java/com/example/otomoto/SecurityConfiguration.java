package com.example.otomoto;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private CustomSuccessHandler successHandler;
    private RepoMyUser repoMyUser;

    public SecurityConfiguration(CustomSuccessHandler successHandler, RepoMyUser repoMyUser) {
        this.successHandler = successHandler;
        this.repoMyUser = repoMyUser;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyDatabaseUserDetailsService(repoMyUser);
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/Neuca/strefaPracownika/**").hasRole("PRACOWNIK")
                        .requestMatchers("/Neuca/strefaLekarza/**").hasRole("LEKARZ")
                        .requestMatchers("/Neuca/strefaAptekarza/**").hasRole("APTEKARZ")
                        .requestMatchers("/Neuca/rejestracja").permitAll()
                        .requestMatchers("/Neuca/addUser").permitAll()
                        .requestMatchers("/Neuca/start").permitAll()
                        .requestMatchers("/Neuca/login").permitAll()
                        .requestMatchers("/Neuca/koszyk").permitAll()
                        .requestMatchers("/Neuca/dodanoUser").permitAll()
                        .requestMatchers("/Neuca/dodanoUser").permitAll()
                        .requestMatchers("/Neuca/dodanoUser").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/Neuca/login")
                        .loginProcessingUrl("/Neuca/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/Neuca/brakDostepu")  // <- Tu dodajesz obsługę 403
                )
                .rememberMe(remember -> remember
                        .tokenValiditySeconds(86400)
                        .key("cos")
                        .rememberMeParameter("remember")
                        .userDetailsService(userDetailsService())
                )
                .logout((logout)->
                        logout.deleteCookies("JSESSIONID")
                                .invalidateHttpSession(false)
                                .logoutUrl("/Neuca/logout")
                                .logoutSuccessUrl("/Neuca/start")
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();


    }

}
