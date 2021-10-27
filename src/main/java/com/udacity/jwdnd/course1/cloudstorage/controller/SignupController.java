package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get mapping function fot /signup
     * @return 'signup' view
     */
    @GetMapping()
    public String signupView() {
        return "signup";
    }

    /**
     * Post mapping function for 'signup'
     * @param user logged in user
     * @param model MVC model
     * @return 'signup' view
     */
    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model,  RedirectAttributes redirectAttributes) {
        String signupError = null;

        // Check if user name is available
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }
        if (signupError == null) {

        }
        if (signupError == null) {
            // Add new user to database
            int rowsAdded = userService.createUser(user);
            // if addition failed
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        // Update model attributes
        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            redirectAttributes.addFlashAttribute("SuccessMessage","Sign Up Successfully");
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupError);
        }

        // Return 'signup' view
        return "signup";
    }
}
