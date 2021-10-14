package com.example.visualplanner.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.visualplanner.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private static EventRepository instance;
    private ArrayList<Event> dataSet = new ArrayList<>();

    public static EventRepository getInstance() {
        if (instance == null) {
            instance = new EventRepository();
        }
        return instance;
    }

    // pretend to get data from webservice or online source.
    public MutableLiveData<List<Event>> getEvents() {
        setEvents();
        MutableLiveData<List<Event>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setEvents() {
        dataSet.add(new Event("Tannlegetime"));
        dataSet.add(new Event("Fotballtrening."));
        dataSet.add(new Event("Raid."));
        dataSet.add(new Event("Rydde rommet."));
        dataSet.add(new Event("Tannlegetime igjen"));
        dataSet.add(new Event("Fotballtrening igjen."));
        dataSet.add(new Event("Raid igjen."));
        dataSet.add(new Event("Rydde rommet igjen."));
    }
}
