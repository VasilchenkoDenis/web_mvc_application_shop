package com.denisvasilchenko.web_mvc_application.service;

import com.denisvasilchenko.web_mvc_application.MyUserDetails;
import com.denisvasilchenko.web_mvc_application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new MyUserDetails(user);
    }
}
