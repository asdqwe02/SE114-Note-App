package vn.edu.uit.noteapp.listeners;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.core.app.NotificationCompat;

import java.io.File;
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
    Note note;
    Context context;
    List<Note> notesList = new ArrayList<>();

    public AlarmReceiver(Note note) {
        this.note = note;
    }

    public AlarmReceiver() {
    }

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

                    if(notesList.get(i).getNoteText() != null) {
                        NotificationCompat.Builder builderNotification = new NotificationCompat.Builder(context, Note_screen.CHANNEL_ID)
                                .setContentIntent(reminderPendingIntent)
                                .setSmallIcon(R.mipmap.ic_easy_note)
                                .setContentTitle(notesList.get(i).getTitle())
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(notesList.get(i).getNoteText()))
                                .setSound(alarmSound)
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                .setGroup(GROUP_KEY_EASYNOTE)
                                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                        notificationManager.notify(getNotificationID(), builderNotification.build());
                    }
//                    if (notesList.get(i).getImagePath() != null){
//                        File sd = Environment.getExternalStorageDirectory();
//                        File image = new File(sd + notesList.get(i).getImagePath(), "");
//
//                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                        Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), bmOptions);
//                        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 250, true);
//
//                        NotificationCompat.Builder builderNotification = new NotificationCompat.Builder(context, Note_screen.CHANNEL_ID)
//                                .setContentIntent(reminderPendingIntent)
//                                .setSmallIcon(R.mipmap.ic_easy_note)
//                                .setContentTitle(notesList.get(i).getTitle())
//                                .setContentText(notesList.get(i).getNoteText())
//                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                                .setAutoCancel(true)
//                                .setLargeIcon(Bitmap.createBitmap(bitmap))
//                                .setStyle(new NotificationCompat.BigPictureStyle()
//                                .bigPicture(bitmap)
//                                .bigLargeIcon(bitmap))
//                                .setSound(alarmSound)
//                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                                .setGroup(GROUP_KEY_EASYNOTE)
//                                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
//                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
//                        notificationManager.notify(getNotificationID(), builderNotification.build());
//                    }
                    else {
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
                    }

                    //**//remove note from the reminder table
                    notesList.get(i).setReminder(false);
                    notesList.get(i).setReminderTime("");
                    notesList.get(i).setReminderDate("no");
                    Listnotes.remove(i);
                    Note_screen temp = new Note_screen(notesList.get(i));
                    temp.saveNote_V2_FromOutside(context);
                }
            }
        }
        new GetNotefromNotification().execute();
    }

    //This function is used to pick a random id for notification
    private int getNotificationID() {
        return (int) new Date().getTime();
    }
}
