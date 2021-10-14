package com.example.visualplanner.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.visualplanner.model.Event;
import com.example.visualplanner.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

public class EventsViewModel extends ViewModel {

    private MutableLiveData<List<Event>> events;
    private EventRepository repo;

    public void init() {
        if (events != null) {
            return;
        }
        repo = EventRepository.getInstance();
        events = repo.getEvents();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }


}
