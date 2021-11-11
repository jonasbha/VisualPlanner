package com.example.visualplanner.model;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Time;
import java.util.Date;

@IgnoreExtraProperties
public class Event {

    private String title;
    private String EventId;
    private String UserId;
    private @ServerTimestamp Date timestamp; // midlertidig sorteringskriterium
    private Date alarmDate;
    private boolean alarmSet;
    private boolean timeSet;
    public Event() {}

    public Event(String title, String eventId, String userId, Date timestamp, Date alarmDate, boolean alarmSet, boolean timeSet) {
        this.title = title;
        EventId = eventId;
        UserId = userId;
        this.timestamp = timestamp;
        this.alarmDate = alarmDate;
        this.alarmSet = alarmSet;
        this.timeSet = timeSet;
    }

    public Event(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getEventId() {
        return EventId;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public boolean isAlarmSet() {
        return alarmSet;
    }

    public void setAlarmSet(boolean alarmSet) {
        this.alarmSet = alarmSet;
    }

    public boolean isTimeSet() {
        return timeSet;
    }

    public void setTimeSet(boolean timeSet) {
        this.timeSet = timeSet;
    }

    public String getUserId() {
        return UserId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
