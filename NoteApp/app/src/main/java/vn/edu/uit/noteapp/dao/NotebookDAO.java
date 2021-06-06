package vn.edu.uit.noteapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;

@Dao
public interface NotebookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Model_Item_Notebook_screen notebook);

    @Delete
    void deleteNote(Model_Item_Notebook_screen note);

}
