package com.youngadessi.demo.security.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //db'den kullanıcı adına göre kullanıcı kaydını getir ve UserDetails (JwtUserDetails) nesnesi oluştur return et

        JwtUserDetails jwtUserDetails=new JwtUserDetails();
        jwtUserDetails.setId("99999");
        jwtUserDetails.setUsername("seyda");
        jwtUserDetails.setPassword("$2a$10$nYgJkUn62KKDRO3Lx./OzOHDMAgxou.PFszCV4sdzwFgsgqv1kw3y");

        return jwtUserDetails;
    }

}
