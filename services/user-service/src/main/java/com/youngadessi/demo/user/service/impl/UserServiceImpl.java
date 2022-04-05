package com.youngadessi.demo.user.service.impl;

import com.youngadessi.demo.user.exception.InvalidRequestException;
import com.youngadessi.demo.user.exception.NotFoundException;
import com.youngadessi.demo.user.model.entity.User;
import com.youngadessi.demo.user.repository.UserRepository;
import com.youngadessi.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

}
