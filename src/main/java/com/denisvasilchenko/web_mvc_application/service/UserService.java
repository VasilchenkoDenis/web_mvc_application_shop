package com.denisvasilchenko.web_mvc_application.service;


import com.denisvasilchenko.web_mvc_application.entity.Role;
import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.repository.RoleRepository;
import com.denisvasilchenko.web_mvc_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

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
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        userRepository.save(user);
        return true;
    }
}
