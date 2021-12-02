package com.example.visualplanner.ui.event.alarm;

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

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Broadcast received.");

        sendNotification(context, intent);
    }

    private void sendNotification(Context context, Intent intent) {
        String title = intent.getStringExtra("title");

        PendingIntent pendingIntent = new NavDeepLinkBuilder(context)
                .setComponentName(MainActivity.class) // dont need it
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.navigation_events)
                .createPendingIntent();

        Notification notification = new NotificationCompat.Builder(context, AppSettings.CHANNEL_1_ID)
                .setSmallIcon(androidx.activity.R.drawable.notification_icon_background)
                .setContentTitle(title)
                .setContentText(intent.getStringExtra("sender"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}
