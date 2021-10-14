package com.example.visualplanner.ui.event;

import androidx.lifecycle.ViewModel;

import com.example.visualplanner.model.Event;

import java.util.ArrayList;

public class EventsViewModel extends ViewModel {

    public ArrayList<Event> getEvents() {

        ArrayList<Event> list = new ArrayList<>();
        list.add(new Event("Tannlegetime"));
        list.add(new Event("Fotballtrening."));
        list.add(new Event("Raid."));
        list.add(new Event("Rydde rommet."));

        return list;
    }
}
