package com.youngadessi.demo.auth.service;

import com.youngadessi.demo.auth.controller.LoginRequest;

public interface AuthenticationService {

    String signin(LoginRequest loginRequest);

//    String signup(User user);
//
//    void delete(String username);
//
//    User whoami(HttpServletRequest req);
//
//    String refresh(String username);

}
