package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    /**
     * Get file 'filename' of user by id 'userid'
     * @param userid user id
     * @param filename file name
     * @return File object
     */
    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    File getFile(Integer userid, String filename);

    /**
     * Get all user's files
     * @param user User object
     * @return List of files
     */
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getUserFiles(User user);

    /**
     * Insert new file to database
     * @param file file object
     * @return newely inserted file primary id
     */
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    /**
     * Delete file with name 'file' with user id 'userid'
     * @param userid User Id
     * @param file File object
     */
    @Delete("DELETE FROM FILES WHERE filename = #{file} AND userid = #{userid};")
    void delete(Integer userid, String file);
}
