package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final HomeController homeController;

    public CredentialController(CredentialService credentialService, UserService userService, HomeController homeController) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.homeController = homeController;
    }

    /**
     * Post mapping function that handles add or edit credential request
     * @param authentication auth. object
     * @param credential cred. object
     * @param model MVC model
     * @return Results' view
     */
    @PostMapping(params="action=addeditcred")
    public String addEditCred(Authentication authentication, Credential credential, Model model) {
        User user = userService.getUser(authentication.getName());
        boolean isCredExist = this.credentialService.isCredExist(credential);
        // Add new cred.
        if(!isCredExist)
        {
            this.credentialService.addCred(user, credential);
        }
        else // Update current Cred.
        {
            this.credentialService.updateCred(credential);
        }

        // update model attributes
        String msg = "Credential " + ( (isCredExist) ? "updated" : "added" ) + " successfully!";
        model.addAttribute("result", "success");
        model.addAttribute("message", msg);
        homeController.updateModel(authentication, model);

        // return results view template
        return "result";
    }

    /**
     * Post mapping function that handles delete credential request
     * @param authentication auth. object
     * @param credential cred. object
     * @param model MVC model
     * @return Results' view
     */
    @PostMapping(params="action=deletecred")
    public String deleteCred(Authentication authentication, Credential credential, Model model) {
        User user = userService.getUser(authentication.getName());
        boolean isCredExist = this.credentialService.isCredExist(credential);
        // delete note
        if(isCredExist)
        {
            this.credentialService.deleteCred(credential);
        }

        // update model attributes
        model.addAttribute("result", "success");
        model.addAttribute("message","Credential is deleted successfully!");
        homeController.updateModel(authentication, model);

        // return results view template
        return "result";
    }
}
