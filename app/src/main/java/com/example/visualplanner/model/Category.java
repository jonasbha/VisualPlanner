package com.example.visualplanner.model;

import java.util.ArrayList;

public class Category {
    String name;
    String color;
    ArrayList<Deadline> deadlines;

    public Category(String name, ArrayList<Deadline> deadlines) {
        this.name = name;
        this.deadlines = deadlines;
    }

    public ArrayList<Deadline> getDeadlines() {
        return deadlines;
    }
}
