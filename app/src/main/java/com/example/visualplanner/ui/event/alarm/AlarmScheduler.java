package com.example.visualplanner.ui.event.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.visualplanner.model.Alarm;

public class AlarmScheduler {

    private static final String TAG = "AlarmScheduler";
    private final AlarmManager alarmManager;
    private Context context;
    private Alarm alarm;

    public AlarmScheduler() {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
    public AlarmScheduler(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void cancel() {
        PendingIntent pendingIntent = getPendingIntent();

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.i(TAG, "alarm canceled");
        }
    }

    protected void start() {
        Log.d(TAG, "alarm pressed");
        if (!alarm.isFinished()) {
            Log.i(TAG, "alarm started.");
            if (alarm.isDateOn() && alarm.isTimeOn()) {
                startExactAlarm();
            } else if (alarm.isTimeOn()) {
                startExactAlarm();
            } else if (alarm.isDateOn()) {
                startInexactAlarm();
            }
        } else cancel();
    }

    private void startExactAlarm() {
        PendingIntent pendingIntent = getPendingIntent();

        if (alarmManager != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms())
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDateTime().getTime(), pendingIntent);
                else alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getDateTime().getTime(), pendingIntent);
            }
            else alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDateTime().getTime(), pendingIntent);
    }

    private void startInexactAlarm() {
        PendingIntent pendingIntent = getPendingIntent();

        if (alarmManager != null)
            alarmManager.set(AlarmManager.RTC, alarm.getDateTime().getTime(), pendingIntent);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this.context, AlarmReceiver.class);
        intent.putExtra("title", "heya");
        return PendingIntent.getBroadcast(this.context, alarm.getRequestCode(), intent,
                PendingIntent.FLAG_IMMUTABLE);
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}
