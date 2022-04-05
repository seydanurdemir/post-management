package com.youngadessi.demo.user.service.impl;

import com.youngadessi.demo.user.exception.CustomJwtException;
import com.youngadessi.demo.user.exception.InvalidRequestException;
import com.youngadessi.demo.user.exception.NotFoundException;
import com.youngadessi.demo.user.model.entity.User;
import com.youngadessi.demo.user.repository.UserRepository;
import com.youngadessi.demo.user.security.JwtTokenProvider;
import com.youngadessi.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        if (user.getUsername() != null && user.getPassword() != null) {
            userRepository.save(user);

            return user;
        } else {
            throw new InvalidRequestException("User");
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        User user_ = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));

        if (user.getUsername() != null && user.getPassword() != null) {
            user_.setUsername(user.getUsername());
            user_.setPassword(user.getPassword());

            userRepository.save(user_);

            return user;
        } else {
            throw new InvalidRequestException("User");
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User")));
    }

    /* Security Related */

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
