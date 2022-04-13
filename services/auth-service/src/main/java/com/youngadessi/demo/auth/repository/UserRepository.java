package com.youngadessi.demo.auth.repository;

import com.youngadessi.demo.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    void deleteByUsername(String username);

}
