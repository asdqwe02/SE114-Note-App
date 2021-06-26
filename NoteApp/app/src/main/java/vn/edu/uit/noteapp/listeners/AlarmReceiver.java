package vn.edu.uit.noteapp.listeners;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Result;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.activity.Note_screen;
import vn.edu.uit.noteapp.activity.Reminder_screen;
import vn.edu.uit.noteapp.bottomsheet.Note_Screen_Bottom_Sheet_Setting;
import vn.edu.uit.noteapp.database.NotesDatabase;
import vn.edu.uit.noteapp.entities.Note;

public class AlarmReceiver extends BroadcastReceiver {

    /**/
    Note note;
    Context context;
    List<Note> notesList = new ArrayList<>();
    public AlarmReceiver(Note note) {
        this.note = note;
    }

    public AlarmReceiver() {
    }

    public int ID;
    //

    @Override
    public void onReceive(Context context, Intent intent) {

        class GetNotefromNotification extends AsyncTask<Void, Void, List<Note>> {
            /**/
            final Note note = new Note();
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(context)
                        .noteDao()
                        .getReminderNote();
            }

            @Override
            protected void onPostExecute(List<Note> Listnotes) {
                super.onPostExecute(Listnotes);
                notesList.addAll(Listnotes);
                notesList.containsAll(Listnotes);

                String GROUP_KEY_EASYNOTE = "Easy note";

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                // Set reminder activity at an intent activity when the notification is clicked
                Intent reminderIntent = new Intent(context, Reminder_screen.class);

                /*Set sound notification*/
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                for (int i = 0; i < 1; i++) {

                    PendingIntent reminderPendingIntent = PendingIntent.getActivity(context, notesList.get(i).getId(), reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builderNotification = new NotificationCompat.Builder(context, Note_screen.CHANNEL_ID)
                            .setContentIntent(reminderPendingIntent)
                            .setSmallIcon(R.mipmap.ic_easy_note)
                            .setContentTitle(notesList.get(i).getTitle())
                            .setContentText(notesList.get(i).getNoteText())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setSound(alarmSound)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                            .setGroup(GROUP_KEY_EASYNOTE)
                            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                    notificationManager.notify(getNotificationID(), builderNotification.build());

                    //**//remove note from the reminder table
                    notesList.get(i).setReminder(false);
                    Listnotes.remove(i);
                    removeReminder(notesList.get(i));
                }
            }
        }
        new GetNotefromNotification().execute();
    }

    //This function is used to pick a random id for notification
    private int getNotificationID() {
        return (int) new Date().getTime();
    }

    /* This function doesn't work */
    public void removeReminder(Note note){
        note.setReminder(false);
        note.setReminderDate("");
        note.setReminderTime("");
        final Note tempNote=note;
        class RemoverReminder extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                Note_screen tempNoteClass = new Note_screen(note);
                tempNoteClass.save
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }
        }

        /**/
    }

}


//Build attribute for the notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Note_screen.CHANNEL_ID)
//                .setContentIntent(reminderPendingIntent)
//                .setSmallIcon(R.mipmap.ic_easy_note)
//                .setContentTitle("Note title")
//                .setContentText("You are having an upcoming activity! Click here for more information")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//                .setSound(alarmSound)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .setGroup(GROUP_KEY_EASYNOTE)
//                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
//
//        notificationManager.notify(getNotificationID(), builder.build());