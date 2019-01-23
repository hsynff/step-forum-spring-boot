package com.step.forum.spring.controller;

import com.step.forum.spring.model.Role;
import com.step.forum.spring.model.User;
import com.step.forum.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute("user")User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(new Role(2));
        user.setToken("asd-asd-asd");
        user.setStatus(1);
        user.setImagePath("dummy/path");

        userService.addUser(user);
        return "redirect:/";
    }
}
