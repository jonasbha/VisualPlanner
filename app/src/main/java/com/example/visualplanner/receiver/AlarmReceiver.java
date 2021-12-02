package com.example.visualplanner.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.visualplanner.AppSettings;
import com.example.visualplanner.MainActivity;
import com.example.visualplanner.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlertReceiver";
    private static int noteId = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Broadcast received with requestCode " + intent.getIntExtra("rq", 0)
                + " and title: " +  intent.getStringExtra("title") + ".");

        sendNotification(context, intent);
    }

    private void sendNotification(Context context, Intent intent) {
        String title = intent.getStringExtra("title");

        PendingIntent destination = new NavDeepLinkBuilder(context)
                .setComponentName(MainActivity.class) // dont need it
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.navigation_events)
                .createPendingIntent();

        Notification notification = new NotificationCompat.Builder(context, AppSettings.CHANNEL_1_ID)
                .setSmallIcon(androidx.activity.R.drawable.notification_icon_background)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // why
                .setCategory(NotificationCompat.CATEGORY_ALARM) // why
                .setGroup("Alarm")
                .setContentIntent(destination)
                .setAutoCancel(true)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(noteId++, notification);
    }
}
