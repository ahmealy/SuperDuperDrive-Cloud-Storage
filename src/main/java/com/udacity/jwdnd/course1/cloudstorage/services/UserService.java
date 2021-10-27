package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * Check if username available
     * @param username User name
     * @return true if available false otherwise
     */
    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    /**
     * Create new user in the database
     * @param user User object
     * @return Newely created user primary id
     */
    public int createUser(User user) {
        // Get random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Get encoded salt
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        // Get hashed password
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        // insert to the database
        return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    /**
     * Get User object by user name
     * @param username User name
     * @return User Object
     */
    public User getUser(String username) {
        return userMapper.getUser(username);
    }
}