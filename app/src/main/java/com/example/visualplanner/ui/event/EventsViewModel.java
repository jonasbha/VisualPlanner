package com.example.visualplanner.ui.event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.model.Event;
import com.example.visualplanner.repository.IEventRepository;

import java.util.List;

public class EventsViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> events;
    private final IEventRepository repo;

    public EventsViewModel() {

        repo = MainActivity.getRepo();
        events = repo.getEvents();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public void deleteEvent(Event e) {
        repo.deleteEvent(e);
    }
}
