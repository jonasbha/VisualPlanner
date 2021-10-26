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
    public Event() {}

    public Event(String title, String eventId, String userId, Date timestamp) {
        this.title = title;
        EventId = eventId;
        UserId = userId;
        this.timestamp = timestamp;
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

    public String getUserId() {
        return UserId;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
