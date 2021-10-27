package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * Get user's files
     * @param user User object
     * @return List of files
     */
    public List<File> getUserFiles(User user) {
        return fileMapper.getUserFiles(user);
    }

    /**
     * Get User file by name
     * @param user User Object
     * @param fileName File name
     * @return File object
     */
    public File getUserFile(User user, String fileName) {
        return fileMapper.getFile(user.getUserId(), fileName);
    }

    /**
     * Delete file by nme
     * @param user User object
     * @param file File name
     */
    public void deleteFile(User user, String file)
    {
        fileMapper.delete(user.getUserId(), file);
    }

    /**
     * Check if file exists in the database
     * @param user User object
     * @param filename File name
     * @return True if it's exists in db false otherwise
     */
    public boolean isFileExist(User user, String filename) {
        return (getUserFile(user, filename) != null);
    }

    /**
     * Insert new file to the database
     * @param user User object
     * @param file File Object
     * @throws IOException
     * @throws SQLException
     */
    public void addFile(User user, MultipartFile file) throws IOException, SQLException {
        // Get clean file path
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        // Get related file data "blob, size,.."
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        String contenttype = file.getContentType();
        String size = "" + file.getSize();

        // Construct nre model file object
        File mFile = new File(-1, filename, contenttype, size, user.getUserId(), bytes);

        // Add the object to the database
        fileMapper.insert(mFile);
    }
}
