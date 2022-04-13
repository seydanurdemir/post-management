package com.youngadessi.demo.auth.service.impl;

import com.youngadessi.demo.auth.exception.CustomJwtException;
import com.youngadessi.demo.auth.model.entity.User;
import com.youngadessi.demo.auth.repository.UserRepository;
import com.youngadessi.demo.auth.jwt.JwtTokenProvider;
import com.youngadessi.demo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
//import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    //private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    /*@PostConstruct
    private void postConstruct() {
        // Sample test user
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword("1234");
        userRepository.save(testUser);
    }*/

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username/*, userRepository.findByUsername(username).getRoles()*/);
        } catch (AuthenticationException e) {
            throw new CustomJwtException("Invalid username/password supplied" + username + password, HttpStatus.BAD_REQUEST);
        }
    }

    public String signup(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            /*user.setRoles(Collections.singletonList(roleRepository.getById(2)));*/
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername()/*, user.getRoles()*/);
        } else {
            throw new CustomJwtException("Username is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(String username) {
        if (!userRepository.existsByUsername(username)) {
            userRepository.deleteByUsername(username);
        } else {
            throw new CustomJwtException("Username is not found", HttpStatus.NOT_FOUND);
        }
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomJwtException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username/*, userRepository.findByUsername(username).getRoles()*/);
    }

}
