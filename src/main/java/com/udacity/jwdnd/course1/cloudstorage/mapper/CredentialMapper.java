package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.util.List;

@Mapper
public interface CredentialMapper {
    /**
     * Get all user's credentials
     * @param user user object
     * @return List of credentials
     */
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getUserCreds(User user);

    /**
     * Get credential by Id
     * @param credential cred. object
     * @return Credential object
     */
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(Credential credential);

    /**
     * Insert new credential
     * @param credential cred. object
     * @return Newely inserted credential primary id
     */
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    /**
     * Delete credential
     * @param credential cred. object
     */
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid};")
    void delete(Credential credential);

    /**
     * Update current credential
     * @param credential cred. object
     */
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid};")
    void updateCredential(Credential credential);
}
