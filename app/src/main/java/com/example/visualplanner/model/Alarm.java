package com.example.visualplanner.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Alarm {

    private Date alarmHolder;
    private Date timeHolder;
    private Date dateHolder;
    private Date dateTime;
    private boolean dateOn;
    private boolean timeOn;
    private boolean dateSet;
    private boolean timeSet;

    public Alarm() {}
    public Alarm(Date alarmHolder, Date timeHolder, Date dateHolder, Date dateTime, boolean dateOn, boolean timeOn, boolean dateSet, boolean timeSet) {
        this.alarmHolder = alarmHolder;
        this.timeHolder = timeHolder;
        this.dateHolder = dateHolder;
        this.dateTime = dateTime;
        this.dateOn = dateOn;
        this.timeOn = timeOn;
        this.dateSet = dateSet;
        this.timeSet = timeSet;
    }

    public Date getAlarmHolder() {
        return alarmHolder;
    }

    public void setAlarmHolder(Date alarmHolder) {
        this.alarmHolder = alarmHolder;
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
                "alarmHolder=" + alarmHolder +
                ", timeHolder=" + timeHolder +
                ", dateHolder=" + dateHolder +
                ", dateTime=" + dateTime +
                ", dateOn=" + dateOn +
                ", timeOn=" + timeOn +
                ", dateSet=" + dateSet +
                ", timeSet=" + timeSet +
                '}';
    }
}
