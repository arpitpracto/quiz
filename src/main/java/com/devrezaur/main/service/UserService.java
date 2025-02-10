

package com.devrezaur.main.service;
import com.devrezaur.main.model.User;

import com.devrezaur.main.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // Find user by username
    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    // Save new user
    public void saveUser(User user) {
        userRepo.save(user);
    }
}
