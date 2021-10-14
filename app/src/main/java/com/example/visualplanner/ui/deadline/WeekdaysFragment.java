package com.example.visualplanner.ui.deadline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        WeekdaysViewModel viewModel = new ViewModelProvider(this).get(WeekdaysViewModel.class);

        binding.setWeekdaysViewModel(viewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }
}
