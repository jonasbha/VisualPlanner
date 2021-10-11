package com.example.visualplanner.ui.deadlines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.visualplanner.R;
import com.example.visualplanner.databinding.WeekdaysBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekdaysFragment extends Fragment {

    WeekdaysBinding binding;

    ArrayList<Character> weekdays = new ArrayList<>(7);

    public WeekdaysFragment() {

    }

    public void init() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.weekdays, container, false);

        init();

        System.out.println(weekdays.toString());

        binding.setWeekdays(weekdays);

        return binding.getRoot();
    }
}
