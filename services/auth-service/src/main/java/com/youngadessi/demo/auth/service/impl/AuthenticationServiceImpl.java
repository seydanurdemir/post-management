package com.youngadessi.demo.auth.service.impl;

import com.youngadessi.demo.auth.controller.LoginRequest;
import com.youngadessi.demo.auth.service.AuthenticationService;
import com.youngadessi.demo.security.config.JwtUserDetails;
import com.youngadessi.demo.security.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public String signin(LoginRequest loginRequest) {

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();

        return  jwtUtils.createToken(jwtUserDetails.getId(),jwtUserDetails.getUsername());


    }

//    public String signup(User user) {
//        if (!userRepository.existsByUsername(user.getUsername())) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            /*user.setRoles(Collections.singletonList(roleRepository.getById(2)));*/
//            userRepository.save(user);
//            return jwtTokenProvider.createToken(user.getUsername()/*, user.getRoles()*/);
//        } else {
//            throw new CustomJwtException("Username is already in use", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    public void delete(String username) {
//        if (!userRepository.existsByUsername(username)) {
//            userRepository.deleteByUsername(username);
//        } else {
//            throw new CustomJwtException("Username is not found", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    public User search(String username) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new CustomJwtException("The user doesn't exist", HttpStatus.NOT_FOUND);
//        }
//        return user;
//    }
//
//    public User whoami(HttpServletRequest req) {
//        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
//    }
//
//    public String refresh(String username) {
//        return jwtTokenProvider.createToken(username/*, userRepository.findByUsername(username).getRoles()*/);
//    }

}
