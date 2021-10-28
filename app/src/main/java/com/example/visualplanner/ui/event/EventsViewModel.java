package com.example.visualplanner.ui.event;

import androidx.lifecycle.ViewModel;

import com.example.visualplanner.model.Event;

import java.util.ArrayList;

public class EventsViewModel extends ViewModel {

    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<String> eventIds = new ArrayList<>();

    public EventsViewModel() {}

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<String> getEventIds() {
        return eventIds;
    }
}
