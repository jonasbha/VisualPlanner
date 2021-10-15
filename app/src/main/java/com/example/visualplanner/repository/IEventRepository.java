package com.example.visualplanner.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.visualplanner.model.Event;

import java.util.List;

public interface IEventRepository {

    MutableLiveData<List<Event>> getEvents();

    void addEvent(Event e);

    void deleteEvent(Event e);
}
