package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;
    private final FileService fileService;
    private final EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, FileService fileService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }

    /**
     * Get mapping function fot /home
     * @param authentication auth. object
     * @param note note object
     * @param credential cred. object
     * @param model MVC model
     * @return 'home' view template or 'login' if error occurs
     */
    @GetMapping()
    public String getHomeView(Authentication authentication, Note note, Credential credential, Model model) {
        try {
            // Update model attributes
            updateModel(authentication, model);
            return "home";
        } catch (Exception e){
            return "login";
       }
    }

    /**
     * Update/fill model for user's data "notes, files, credentials"
     * @param authentication auth. object
     * @param model MVC model
     */
    public void updateModel(Authentication authentication, Model model)
    {
        // Get logged in user
        User user = userService.getUser(authentication.getName());

        // Update model data
        model.addAttribute("userNotes", this.noteService.getUserNotes(user));
        model.addAttribute("userCreds", this.credentialService.getUserCreds(user));
        model.addAttribute("userFiles", this.fileService.getUserFiles(user));
        model.addAttribute("encryptionService", encryptionService);
    }

    /**
     * Error mapping
     * @param auth auth. object
     * @return 'login' view
     */
    @RequestMapping("/error")
    public String errorMapping(Authentication auth) {
        return "login";
    }

}
