package vn.edu.uit.noteapp.listeners;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.edu.uit.noteapp.dao.NotebookDAO;
import vn.edu.uit.noteapp.data.Model_Item_Notebook_screen;

@Database(entities = {Model_Item_Notebook_screen.class}, version =  1, exportSchema = false)
public abstract class NotebooksDatabase extends RoomDatabase {

    private static NotebooksDatabase notebooksDatabase;

    public static synchronized NotebooksDatabase getNotebooksDatabase(Context context){

        if(notebooksDatabase == null)
            notebooksDatabase = Room.databaseBuilder(
                    context,
                    NotebooksDatabase.class,
                    "notebook_db"
            ).build();
        return notebooksDatabase;
    }
    public abstract NotebookDAO notebookDAO();
}
