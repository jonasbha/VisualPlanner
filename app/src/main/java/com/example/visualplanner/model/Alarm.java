package com.example.visualplanner.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class Alarm {

    private Date dateTimeHolder, timeHolder, dateHolder, dateTime;
    private boolean dateOn, timeOn, dateSet, timeSet, isFinished;
    private int requestCode;

    public Alarm() {
        this.requestCode = getFakeUnique();
    }

    public void update() {
        if (dateOn && timeOn) {
            dateTime = dateTimeHolder;
            isFinished = dateTime.before(Calendar.getInstance().getTime());
        } else if (dateOn) {
            dateTime = dateHolder;
        } else if (timeOn) {
            dateTime = timeHolder;
            isFinished = dateTime.before(Calendar.getInstance().getTime());
        } else {
            dateTime = null;
            isFinished = false;
        }
    }

    /**
     * Method to simulate an unique request code as integer.
     * The integer is in theory not unique and serves as a substitute for an actual unique integer value.
     *
     * The integer value is ideally generated as an unique value within the range of active alarms for each user.
     * To achieve this a user model would be created based on the uid from authentication with a counter for each active alarm.
     */
    private int getFakeUnique() {
        return (int) Math.floor(Math.random() * 2147483646);
    }

    public Date getDateTimeHolder() {
        return dateTimeHolder;
    }

    public void setDateTimeHolder(Date dateTimeHolder) {
        this.dateTimeHolder = dateTimeHolder;
    }

    public Date getTimeHolder() {
        return timeHolder;
    }

    public void setTimeHolder(Date timeHolder) {
        this.timeHolder = timeHolder;
    }

    public Date getDateHolder() {
        return dateHolder;
    }

    public void setDateHolder(Date dateHolder) {
        this.dateHolder = dateHolder;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isDateOn() {
        return dateOn;
    }

    public void setDateOn(boolean dateOn) {
        this.dateOn = dateOn;
    }

    public boolean isTimeOn() {
        return timeOn;
    }

    public void setTimeOn(boolean timeOn) {
        this.timeOn = timeOn;
    }

    public boolean isDateSet() {
        return dateSet;
    }

    public void setDateSet(boolean dateSet) {
        this.dateSet = dateSet;
    }

    public boolean isTimeSet() {
        return timeSet;
    }

    public void setTimeSet(boolean timeSet) {
        this.timeSet = timeSet;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmHolder=" + dateTimeHolder +
                ", timeHolder=" + timeHolder +
                ", dateHolder=" + dateHolder +
                ", dateTime=" + dateTime +
                ", dateOn=" + dateOn +
                ", timeOn=" + timeOn +
                ", dateSet=" + dateSet +
                ", timeSet=" + timeSet +
                '}';
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
