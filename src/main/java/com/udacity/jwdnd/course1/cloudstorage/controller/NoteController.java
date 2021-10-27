package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    private UserService userService;
    private HomeController homeController;

    public NoteController(NoteService noteService, UserService userService, HomeController homeController) {
        this.noteService = noteService;
        this.userService = userService;
        this.homeController = homeController;
    }

    /**
     * Post mapping function that handles add or edit note request
     * @param authentication auth. object
     * @param note note object
     * @param model MVC model
     * @return 'result' view
     */
    @PostMapping(params="action=addeditnote")
    public String addEditNote(Authentication authentication, Note note, Model model) {
        // Get logged in user
        User user = userService.getUser(authentication.getName());
        // Check if note already exists
        boolean isNoteExist = this.noteService.isNoteExist(note);
        // Add new note
        if(!isNoteExist)
        {
            this.noteService.addNote(user, note);
        }
        else // Update current Note
        {
            this.noteService.updateNote(note);
        }

        // Update model attributes
        String msg = "Note " + ( (isNoteExist) ? "updated" : "added" ) + " successfully!";
        model.addAttribute("result", "success");
        model.addAttribute("message",msg);
        homeController.updateModel(authentication, model);

        // Return 'result' view template
        return "result";
    }

    /**
     * Post mapping function that handles delete note request
     * @param authentication auth. object
     * @param note note object
     * @param model MVC model
     * @return 'result' view
     */
    @PostMapping(params="action=deletenote")
    public String deleteNote(Authentication authentication, Note note, Model model) {
        // Get logged in user
        User user = userService.getUser(authentication.getName());
        // Check if note already exists
        boolean isNoteExist = this.noteService.isNoteExist(note);
        // delete note
        if(isNoteExist)
        {
            this.noteService.deleteNote(note);
        }

        // Update model attributes
        model.addAttribute("result", "success");
        model.addAttribute("message","Note deleted successfully!");
        homeController.updateModel(authentication, model);

        // Return 'result' view template
        return "result";
    }
}
