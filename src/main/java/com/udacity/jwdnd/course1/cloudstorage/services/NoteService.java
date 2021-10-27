package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * Add note to the database
     * @param user User object
     * @param note Note object
     */
    public void addNote(User user, Note note) {
        note.setUserid(user.getUserId());
        noteMapper.insert(note);
    }

    /**
     * Update Object
     * @param note Note Object
     */
    public void updateNote(Note note)
    {
        noteMapper.updateNote(note);
    }

    /**
     * Delete note
     * @param note Note Object
     */
    public void deleteNote(Note note)
    {
        noteMapper.delete(note);
    }

    // Get all user's notes
    public List<Note> getUserNotes(User user) {
        return noteMapper.getUserNotes(user);
    }

    // Check if note exist
    public boolean isNoteExist(Note note)
    {
        return (noteMapper.getNote(note) != null);
    }
}
