package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final HomeController homeController;
    private final UserService userService;

    public FileController(FileService fileService, HomeController homeController, UserService userService) {
        this.fileService = fileService;
        this.homeController = homeController;
        this.userService = userService;
    }

    /**
     * Post mapping function that handles uploading new file request
     * @param authentication auth. object
     * @param newFile uploaded file
     * @param model MVC model
     * @return Results' view
     * @throws SQLException
     * @throws IOException
     */
    @PostMapping(params="action=uploadfile")
    public String uploadFile(Authentication authentication, @RequestParam("file") MultipartFile newFile, Model model) throws SQLException, IOException {
        try {
            // Get logged in user
            User user = userService.getUser(authentication.getName());

            // Get clean path file name
            String filename = StringUtils.cleanPath(newFile.getOriginalFilename());

            // Check if file already exists in database
            boolean isFileExist = fileService.isFileExist(user, newFile.getOriginalFilename());

            String errorStr = "";
            String result = "";
            if(isFileExist)
            {
                result = "error";
                errorStr = "file already exists";
            }
            else if (newFile.isEmpty())
            {
                result = "error";
                errorStr = "You did not choose a file or the file is empty!";
            }
            else if (filename.contains("..")) {
                result = "error";
                errorStr = "Can't store file with relative path outside current directory " + filename;
            }
            else{
                // Add file to database
                fileService.addFile(user, newFile);
                result = "success";
                errorStr = "File '"+ filename + "' uploaded successfully!";
            }

            // Update model attributes
            model.addAttribute("result", result);
            model.addAttribute("message", errorStr);
            homeController.updateModel(authentication, model);
        }
        catch (Exception e)
        {
            homeController.updateModel(authentication, model);
            model.addAttribute("message",
                    "Failed to store file, try again!");

        }

        // Return results view template
        return "result";
    }

    /**
     * Post mapping function that handles downloading a file request
     * @param fileName File name
     * @param authentication auth. Object
     * @return Results' view
     */
    @GetMapping(value = "/viewFile/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName, Authentication authentication) {
        // Get logged in user
        User user = userService.getUser(authentication.getName());
        // Download file
        return fileService.getUserFile(user, fileName).getFiledata();
    }

    /**
     * Post mapping function that handles deleting a file
     * @param authentication auth. object
     * @param fileName File name
     * @param model MVC model
     * @return Results' view
     */
    @GetMapping(value = "/deleteFile/{fileName}")
    public String deleteFile(Authentication authentication, @PathVariable String fileName, Model model) {
        // Get logged in user
        User user = userService.getUser(authentication.getName());
        // Delete file
        fileService.deleteFile(user, fileName);

        // Update model attributes
        String msg = "File '" + fileName + "' deleted successfully!";
        model.addAttribute("files", fileService.getUserFiles(user));
        model.addAttribute("result", "success");
        model.addAttribute("message", msg);

        // Return results view template
        return "result";
    }
}
