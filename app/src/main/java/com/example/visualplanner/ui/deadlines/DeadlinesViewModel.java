package com.example.visualplanner.ui.deadlines;

import androidx.lifecycle.ViewModel;

import com.example.visualplanner.model.Deadline;

import java.util.ArrayList;
import java.util.Calendar;

public class DeadlinesViewModel extends ViewModel {

    ArrayList<Deadline> deadlines = new ArrayList<>();

    public void init() {
        char[] weekdays = new char[7];

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < 7; i++) {
            if (day > 7)
                day = 1;
            weekdays[i] = getWeekdayLetter(day++);
        }
    }

    public char getWeekdayLetter(int day) {
        switch (day) {
            case Calendar.MONDAY:
                return 'M';
            case Calendar.TUESDAY:
            case Calendar.THURSDAY:
                return 'T';
            case Calendar.WEDNESDAY:
                return 'W';
            case Calendar.FRIDAY:
                return 'F';
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                return 'S';
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

}

