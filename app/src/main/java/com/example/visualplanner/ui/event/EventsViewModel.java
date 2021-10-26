package com.example.visualplanner.ui.event;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.visualplanner.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsViewModel extends ViewModel {

    private final ArrayList<Event> events = new ArrayList<>();

    public EventsViewModel() {}

    public MutableLiveData<List<Event>> getEvents() {
        MutableLiveData<List<Event>> data = new MutableLiveData<>();
        data.setValue(events);
        return data;
    }

    public void addEvent(Event e) {
        events.add(e);
    }
}
