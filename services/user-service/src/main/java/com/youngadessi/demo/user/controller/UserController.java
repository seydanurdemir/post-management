package com.youngadessi.demo.user.controller;

import com.youngadessi.demo.user.model.dto.UserDTO;
import com.youngadessi.demo.user.model.entity.User;
import com.youngadessi.demo.user.model.mapper.UserMapper;
import com.youngadessi.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @GetMapping(value = "/getOK")
    public HttpStatus getSampleUser() {
        return HttpStatus.OK;
    }

    /*@GetMapping(value = "/{id}")
    public ResponseEntity<User> getSampleUser(@PathVariable Long id) {
        return new ResponseEntity(HttpStatus.OK);
    }*/

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return allUsers.stream().map(USER_MAPPER::toDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public UserDTO getUser(@PathVariable @Min(1) Long id) {
        return USER_MAPPER.toDto(userService.getUser(id));
    }

    @PostMapping
    public void saveUser(@Valid @RequestBody UserDTO user) {
        userService.addUser(USER_MAPPER.toEntity(user));
    }

    @PutMapping
    public UserDTO updateUser(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            throw new RuntimeException("User id can not be null for update!");
        }
        return USER_MAPPER.toDto(userService.updateUser(user));
    }

    @DeleteMapping
    public boolean deletePost(@RequestParam @Min(1) Long id) {
        return userService.deleteUser(id);
    }

}
