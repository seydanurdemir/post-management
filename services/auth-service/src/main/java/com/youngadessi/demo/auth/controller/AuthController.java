package com.youngadessi.demo.auth.controller;

import com.youngadessi.demo.auth.model.dto.UserDTO;
import com.youngadessi.demo.auth.model.mapper.UserMapper;
import com.youngadessi.demo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    /* Security Related */

    @PostMapping("/signin")
    public String signin(@Valid @RequestBody UserDTO userDTO) {
        return authService.signin(userDTO.getUsername(), userDTO.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserDTO userDTO) {
        return authService.signup(USER_MAPPER.toEntity(userDTO));
    }

    @DeleteMapping(value = "/{username}")
    public String delete(@PathVariable String username) {
        authService.delete(username);
        return username;
    }

    @GetMapping(value = "/me")
    public UserDTO whoami(HttpServletRequest req) {
        return USER_MAPPER.toDto(authService.whoami(req));
    }

}
