package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * Get mapping function fot /login
     * @return 'login' view
     */
    @GetMapping()
    public String loginView() {
        return "login";
    }

    /**
     * Post mapping function for login
     * @param user logged in user
     * @param model MVC model
     * @return 'home' view
     */
    @PostMapping()
    public String loginUser(@ModelAttribute User user, Model model) {
        return "home";
    }
}
