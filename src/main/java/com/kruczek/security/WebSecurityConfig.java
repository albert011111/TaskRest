package com.kruczek.security;

import com.kruczek.security.jwt.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // oznacza klasę jako definicję beana dla contextu aplikacji
@EnableWebSecurity
//wlacza security z poziomu wykonania metody --> w sensie, ze sprawdza czy dany uzytkownik moze wykonac metode
@EnableGlobalMethodSecurity(prePostEnabled = true/*pozwala na adnotacje @PreAuthorize nad metodami*/)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter (){
        return  new JwtAuthTokenFilter();
    }
}
