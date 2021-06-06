package vn.edu.uit.noteapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.edu.uit.noteapp.dao.ReminderDao;
import vn.edu.uit.noteapp.entities.Reminders;

@Database(entities = Reminders.class, version = 1, exportSchema = false)
public abstract class ReminderDatabase extends RoomDatabase {

    private static ReminderDatabase reminderDatabase;

    public static synchronized ReminderDatabase getReminderDatabase(Context context){
        if (reminderDatabase == null){
            reminderDatabase = Room.databaseBuilder(
                    context,
                    ReminderDatabase.class,
                    "reminders_db"
            ).build();
        }
        return reminderDatabase;
    }

    public abstract ReminderDao reminderDao();
}
