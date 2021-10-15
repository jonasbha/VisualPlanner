package com.example.visualplanner.ui.event;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.model.Event;
import com.example.visualplanner.repository.FakeEventRepository;
import com.example.visualplanner.repository.IEventRepository;

import java.util.List;

public class EventsViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> events;

    public EventsViewModel() {

        IEventRepository repo = MainActivity.getRepo();
        events = repo.getEvents();
    }

    public void addEvent() {

    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }


}
