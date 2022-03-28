package com.youngadessi.demo.user.service.impl;

import com.youngadessi.demo.user.model.entity.User;
import com.youngadessi.demo.user.repository.UserRepository;
import com.youngadessi.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.delete(getUser(id));
        return true;
    }
}
