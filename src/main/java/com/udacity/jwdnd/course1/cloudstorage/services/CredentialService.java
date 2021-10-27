package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credMapper, EncryptionService encryptionService) {
        this.credMapper = credMapper;
        this.encryptionService = encryptionService;
    }

    /**
     * Add credential service
     * @param user User object
     * @param cred Credential object
     */
    public void addCred(User user, Credential cred) {
        // Get random key
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        // Get encoded key
        String encodedKey = Base64.getEncoder().encodeToString(key);

        // Get encrypted password
        String encryptedPassword = encryptionService.encryptValue(cred.getPassword(), encodedKey);

        // Update cred object
        cred.setPassword(encryptedPassword);
        cred.setKey(encodedKey);
        cred.setUserid(user.getUserId());

        // insert to database through the mapper
        credMapper.insert(cred);
    }

    /**
     * Update credential service
     * @param cred Credential object
     */
    public void updateCred(Credential cred)
    {
        // Get encrypted password
        String encryptedPassword = encryptionService.encryptValue(cred.getPassword(), cred.getKey());

        // Update cred object
        cred.setPassword(encryptedPassword);

        // Update cred in database
        credMapper.updateCredential(cred);
    }

    /**
     * Delete Credential
     * @param cred
     */
    public void deleteCred(Credential cred)
    {
        credMapper.delete(cred);
    }

    /**
     * Get User's Credentials
     * @param user User object
     * @return List of Credentials
     */
    public List<Credential> getUserCreds(User user) {
        // Get List of user's credentials
        List<Credential> creds = credMapper.getUserCreds(user);

        return creds;
    }

    /**
     * Check if credential 'cred' exists in the database
     * @param cred Credential object
     * @return true if exist in the db else otherwise
     */
    public boolean isCredExist(Credential cred)
    {
        return (credMapper.getCredential(cred) != null);
    }
}
