package com.youngadessi.demo.auth.service;

import com.youngadessi.demo.auth.model.entity.User;
import javax.servlet.http.HttpServletRequest;


public interface AuthService {

    String signin(String username, String password);

    String signup(User user);

    void delete(String username);

    User whoami(HttpServletRequest req);

    String refresh(String username);

}
