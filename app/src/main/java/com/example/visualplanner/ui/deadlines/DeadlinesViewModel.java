package com.example.visualplanner.ui.deadlines;

import androidx.lifecycle.ViewModel;

import com.example.visualplanner.model.Category;
import com.example.visualplanner.model.Deadline;

import java.util.ArrayList;
import java.util.Calendar;

public class DeadlinesViewModel extends ViewModel {

    public ArrayList<Category> getCategories() {
        ArrayList<Deadline> deadlines1 = new ArrayList<>();
        ArrayList<Deadline> deadlines2 = new ArrayList<>();
        ArrayList<Deadline> deadlines3 = new ArrayList<>();
        ArrayList<Category> strings = new ArrayList<>();

        deadlines1.add(new Deadline("somethgg", 1, 2));
        deadlines1.add(new Deadline("some", 4, 2));
        deadlines2.add(new Deadline("bla bla", 5));
        deadlines3.add(new Deadline("ehhh", 3));

        strings.add(new Category("ITF 1234", deadlines1));
        strings.add(new Category("Mobilprog", deadlines2));
        strings.add(new Category("ITF 5432", deadlines3));

        return strings;
    }
}

