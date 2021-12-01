package com.example.visualplanner.ui.event.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmScheduler {

    private static final String TAG = "AlertHandler";
    private final AlarmManager alarmManager;
    private Context context;
    private int requestCode;

    public AlarmScheduler() {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    public AlarmScheduler(Context context, int requestCode) {
        this.context = context;
        this.requestCode = requestCode;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    protected void startExactAlarm(long alarm) {
        Intent intent = new Intent(this.context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent,
                PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms())
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm, pendingIntent);
                else alarmManager.set(AlarmManager.RTC_WAKEUP, alarm, pendingIntent);
            }
            else alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm, pendingIntent);
    }

    protected void startInexactAlarm(long alarm) {
        Intent intent = new Intent(this.context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent,
                PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null)
            alarmManager.set(AlarmManager.RTC, alarm, pendingIntent);
    }

    protected void cancelAlarm() {
        Intent intent = new Intent(this.context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, requestCode, intent,
                PendingIntent.FLAG_IMMUTABLE);

        if (pendingIntent != null && alarmManager != null)
            alarmManager.cancel(pendingIntent);
    }
}
