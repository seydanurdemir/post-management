package com.youngadessi.demo.user.service;

import com.youngadessi.demo.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    Page<User> getAllUsers(Pageable pageable);

    User getUser(Long id);

    User saveUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

}
