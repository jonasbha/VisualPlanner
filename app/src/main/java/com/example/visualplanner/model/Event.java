package com.example.visualplanner.model;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Event {

    private String title;
    private String EventId;
    private String UserId;
    private @ServerTimestamp Date timestamp; // midlertidig sorteringskriterium
    private Date alarm;
    private boolean dateSet;
    private boolean timeSet;
    public Event() {}

    public Event(String title, String eventId, String userId, Date timestamp, Date alarm, boolean dateSet, boolean timeSet) {
        this.title = title;
        EventId = eventId;
        UserId = userId;
        this.timestamp = timestamp;
        this.alarm = alarm;
        this.dateSet = dateSet;
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

    public Date getAlarm() {
        return alarm;
    }

    public void setAlarm(Date alarm) {
        this.alarm = alarm;
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
