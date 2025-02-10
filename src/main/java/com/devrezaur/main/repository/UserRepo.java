package com.devrezaur.main.repository;

import com.devrezaur.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  // Find user by username

    List<User> findByUserRole(String role);
    List<User> findByUserId(Long userId);

}

