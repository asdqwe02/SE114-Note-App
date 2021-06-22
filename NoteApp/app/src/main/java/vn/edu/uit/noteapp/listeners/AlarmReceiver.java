package vn.edu.uit.noteapp.listeners;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import vn.edu.uit.noteapp.R;
import vn.edu.uit.noteapp.activity.Reminder_screen;
import vn.edu.uit.noteapp.activity.Repeating_activity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeatingIntent = new Intent(context, Reminder_screen.class);

        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "reminderNotify")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_easy_note)
                .setContentTitle("Notification title")
                .setContentText("Notification text")
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }
}
