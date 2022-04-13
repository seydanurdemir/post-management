package com.youngadessi.demo.auth.controller;

import com.youngadessi.demo.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    private final PasswordEncoder passwordEncoder;

//    private static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);


    @PostMapping("/signin")
    public ResponseEntity<String> signin(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.signin(loginRequest), HttpStatus.CREATED);
    }

    @PostMapping("/secure")
    public ResponseEntity<String> secure() {
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }


//    @PostMapping("/signup")
//    public String signup(@RequestBody @Valid UserDTO userDTO) {
//        return authService.signup(USER_MAPPER.toEntity(userDTO));
//    }
//
//    @DeleteMapping(value = "/{username}")
//    public String delete(@PathVariable String username) {
//        authService.delete(username);
//        return username;
//    }
//
//    @GetMapping(value = "/me")
//    public UserDTO whoami(HttpServletRequest req) {
//        return USER_MAPPER.toDto(authService.whoami(req));
//    }

}
