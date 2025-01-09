package com.denisvasilchenko.web_mvc_application.service;


import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User savedUser = userRepository.findUserByName(user.getName());
        if (savedUser != null) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }
}
