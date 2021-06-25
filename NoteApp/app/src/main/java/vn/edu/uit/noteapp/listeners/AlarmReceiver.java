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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.activity.Note_screen;
import vn.edu.uit.noteapp.activity.Reminder_screen;
import vn.edu.uit.noteapp.database.NotesDatabase;
import vn.edu.uit.noteapp.entities.Note;

public class AlarmReceiver extends BroadcastReceiver {

    /**/
    Note note;
    List<Note> notesList = new ArrayList<>();

    public AlarmReceiver(Note note) {
        this.note = note;
    }

    public AlarmReceiver() {
    }
    //

    @Override
    public void onReceive(Context context, Intent intent) {

        int flag;

        /**/
        class GetNotefromNotification extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getNotesDatabase(context)
                        .noteDao().getReminderNote();
            }

            @Override
            protected void onPostExecute(List<Note> Listnotes) {
                super.onPostExecute(Listnotes);
                notesList.addAll(Listnotes);
                notesList.containsAll(Listnotes);

                String GROUP_KEY_EASYNOTE = "Easy note";

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                // Create an Intent for the activity you want to start
                Intent reminderIntent = new Intent(context, Reminder_screen.class);

                /*Set sound notification*/
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                for (int i = 0; i < 1; i++) {

                    PendingIntent reminderPendingIntent = PendingIntent.getActivity(
                            context, notesList.get(i).getId(), reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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

                    //Delete the reminder note that has been notified
                    notesList.get(i).setReminder(false);
                }
            }

        }
        new GetNotefromNotification().execute();

    }

    private int getNotificationID() {
        return (int) new Date().getTime();
    }

}


//App ko chạy notification test debug để tìm lỗi

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