package vn.edu.uit.noteapp.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vn.edu.uit.noteapp.entities.Reminders;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM Reminders ORDER BY id DESC")
    List<Reminders> getAllReminders();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReminder(Reminders reminders);

    @Delete
    void deleteReminder(Reminders reminders);
}
