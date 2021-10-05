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

public class WeekdaysFragment extends Fragment {

    WeekdaysBinding binding;

    public WeekdaysFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.weekdays, container, false);

        binding.setWeekday("W");

        return binding.getRoot();
    }
}
