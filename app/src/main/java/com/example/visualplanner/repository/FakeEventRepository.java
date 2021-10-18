package com.example.visualplanner.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.visualplanner.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Support class for testing repository independent of database implementation.
 */
public class FakeEventRepository implements IEventRepository {

    private static FakeEventRepository instance;
    private final ArrayList<Event> dataSet = new ArrayList<>();

    public FakeEventRepository() {
        dataSet.add(new Event("Tannlegetime"));
        dataSet.add(new Event("Fotballtrening."));
        dataSet.add(new Event("Raid."));
        dataSet.add(new Event("Rydde rommet."));
    }

    public static FakeEventRepository getInstance() {
        if (instance == null) {
            instance = new FakeEventRepository();
        }
        return instance;
    }

    // pretend to get data from webservice or online source.
    @Override
    public MutableLiveData<List<Event>> getEvents() {
        MutableLiveData<List<Event>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    @Override
    public void addEvent(Event e) {
        dataSet.add(e);
    }

    @Override
    public void deleteEvent(Event e) {
        dataSet.remove(e);
    }
}
