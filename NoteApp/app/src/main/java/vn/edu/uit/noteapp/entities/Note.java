package vn.edu.uit.noteapp.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "note_text")
    private String noteText;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    @ColumnInfo(name = "date_time")
    private String dateTime;

    @ColumnInfo(name = "CRI_state")
    private boolean CRIstate;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark;

    @ColumnInfo(name = "Notebook")
    private String notebook;

    //reminder column
    @ColumnInfo (name = "Reminder")
    private boolean reminder;

    @ColumnInfo (name = "reminder_Date")
    private String reminderDate;

    @ColumnInfo (name = "reminder_Time")
    private String reminderTime;

    public boolean isReminder() { return reminder; }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) { this.reminderDate = reminderDate; }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
    //end

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public String getNotebook() {
        return notebook;
    }

    public void setNotebook(String notebook) {
        this.notebook = notebook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCRIstate() {
        return CRIstate;
    }

    public void setCRIstate(boolean CRIstate) {
        this.CRIstate = CRIstate;
    }

    @NonNull
    @Override
    public String toString() {
        return id + title + " : " + dateTime;
    }
}
