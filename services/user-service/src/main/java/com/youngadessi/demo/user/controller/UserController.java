package com.youngadessi.demo.user.controller;

import com.youngadessi.demo.user.model.dto.UserDTO;
import com.youngadessi.demo.user.model.entity.User;
import com.youngadessi.demo.user.model.mapper.UserMapper;
import com.youngadessi.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@PageableDefault(page = 0, size = 3) @SortDefault.SortDefaults({ @SortDefault(sort = "username", direction = Sort.Direction.ASC), @SortDefault(sort = "id", direction = Sort.Direction.ASC) }) Pageable pageable) {
        return new ResponseEntity<>(userService.getAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable @Min(1) Long id) {
        return new ResponseEntity<>(USER_MAPPER.toDto(userService.getUser(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {
       return new ResponseEntity<>(USER_MAPPER.toDto(userService.saveUser(USER_MAPPER.toEntity(userDTO))), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable(value = "id") @Min(1) Long id, @Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(USER_MAPPER.toDto(userService.updateUser(id, USER_MAPPER.toEntity(userDTO))), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") @Min(1) Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Security Related */

    @PostMapping("/signin")
    public String signin(@Valid @RequestBody UserDTO userDTO) {
        return userService.signin(userDTO.getUsername(), userDTO.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserDTO userDTO) {
        return userService.signup(USER_MAPPER.toEntity(userDTO));
    }

    @DeleteMapping(value = "/{username}")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping(value = "/me")
    public UserDTO whoami(HttpServletRequest req) {
        return USER_MAPPER.toDto(userService.whoami(req));
    }

}
