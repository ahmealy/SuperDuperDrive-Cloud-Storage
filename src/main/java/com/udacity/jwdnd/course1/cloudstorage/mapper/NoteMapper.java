package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface NoteMapper {
    /**
     * Get all user's notes
     * @param user User object
     * @return List of notes
     */
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getUserNotes(User user);

    /**
     * Get note by Id
     * @param note Note object holding the id
     * @return Actual note object
     */
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(Note note);

    /**
     * Insert new note to database
     * @param note Note object
     * @return newely added note primary key
     */
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    /**
     * Delete Note
     * @param note Note object
     */
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid};")
    void delete(Note note);

    /**
     * Update Current Note
     * @param note Note Object
     */
    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid};")
    void updateNote(Note note);
}