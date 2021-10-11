package com.example.visualplanner.ui.deadlines;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekdaysViewModel extends ViewModel {

    private final ArrayList<Character> weekdays = new ArrayList<>(7);

    public WeekdaysViewModel() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < 7; i++) {
            if (day > 7)
                day = 1;
            weekdays.add(getWeekdayLetter(day++));
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

    public ArrayList<Character> getWeekdays() {
        return weekdays;
    }
}