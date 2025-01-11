package com.denisvasilchenko.web_mvc_application.controller;

import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        //тут и во всех других похожих местах, ты возвращаешь Entity на фронт, так не делается
        //почитай что такой Data Transfer Object (еще называют DTO)
        model.addAttribute("allUsers", userService.findAllUsers());
        return "admin";
    }

    @GetMapping("/admin/addUser")
    public String showAddUserForm() {
        return "addUser";
    }

    @PostMapping("/admin/addUser")
    public String addUserToDataBase(@RequestParam String username, @RequestParam String surname, @RequestParam String role, @RequestParam String password) {
        if (username != null && surname != null && role != null) {
            if (userService.saveUser(new User(username, surname, role, password))) {
                return "redirect:/admin";
            }
        }
        return "redirect:/admin/addUser";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/updateUser/{id}")
    public String showFormToUpdate(@PathVariable int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

}
