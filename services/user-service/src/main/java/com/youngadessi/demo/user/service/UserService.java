package com.youngadessi.demo.user.service;

import com.youngadessi.demo.user.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    void addUser(User user);

    User updateUser(User user);

    boolean deleteUser(Long id);

}
