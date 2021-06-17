package vn.edu.uit.noteapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import vn.edu.uit.noteapp.activity.Note_screen;
import vn.edu.uit.noteapp.activity.Repeating_activity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //edit from here
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, Repeating_activity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, 100,repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //end

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "reminder")
                        .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_easy_note)
                .setContentTitle("Easy note")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);//use to canel the notification when users swipe it away

//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
//        notificationManagerCompat.notify(123, NotificationBuilder.build());

        notificationManager.notify(100,builder.build());
    }
}
