package com.example.visualplanner.ui.deadline;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekdaysViewModel extends ViewModel {

    private final ArrayList<Character> weekdays = new ArrayList<>(7);

    public WeekdaysViewModel() {
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < 7; i++) {
            if (today > 7)
                today = 1;
            weekdays.add(getWeekdayLetter(today++));
        }
    }

    public static char getWeekdayLetter(int day) {
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

    public ArrayList<Character> getWeekdays() {
        return weekdays;
    }
}