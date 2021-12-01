package com.example.visualplanner.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Event {

    private String title;
    private String EventId;
    private String UserId;
    private Alarm alarm;

    public Event() {
        this.alarm = new Alarm();
    }
    public Event(String title) {
        this.title = title;
        this.alarm = new Alarm();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", EventId='" + EventId + '\'' +
                ", UserId='" + UserId + '\'' +
                ", alarm=" + alarm +
                '}';
    }
}
