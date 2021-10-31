package com.example.visualplanner.ui.event;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.visualplanner.model.Event;

import java.util.ArrayList;

public class EventsViewModel extends ViewModel {

    private ArrayList<Event> events;
    private final ArrayList<String> eventIds;

    public EventsViewModel() {
        Log.d("viewmodel init", "called ");
        events = new ArrayList<>();
        eventIds = new ArrayList<>();
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<String> getEventIds() {
        return eventIds;
    }

    public void refresh() {
        events = new ArrayList<>();
    }
}
