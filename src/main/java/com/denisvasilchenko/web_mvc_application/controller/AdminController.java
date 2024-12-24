package com.denisvasilchenko.web_mvc_application.controller;

import com.denisvasilchenko.web_mvc_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.findAllUsers());
        return "admin";
    }
}
