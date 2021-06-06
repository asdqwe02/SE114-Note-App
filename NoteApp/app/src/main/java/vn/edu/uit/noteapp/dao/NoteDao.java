package vn.edu.uit.noteapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vn.edu.uit.noteapp.entities.Note;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAllNotes();

    @Query("SELECT * FROM notes WHERE bookmark = 1 ORDER BY ID DESC")
    List<Note> getBookmarkNotes();

    @Query("SELECT * FROM notes WHERE Notebook = :name ORDER BY ID DESC")
    List<Note> getNotebookNote(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);
}
