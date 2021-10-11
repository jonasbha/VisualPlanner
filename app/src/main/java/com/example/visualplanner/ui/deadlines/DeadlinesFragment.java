package com.example.visualplanner.ui.deadlines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.R;

public class DeadlinesFragment extends Fragment {

    public DeadlinesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DeadlinesViewModel viewModel = new ViewModelProvider(this).get(DeadlinesViewModel.class);
        viewModel.init();
        return inflater.inflate(R.layout.fragment_deadlines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton drawerBtn = view.findViewById(R.id.openDrawerBtn);
        drawerBtn.setOnClickListener(v -> ((MainActivity) requireActivity()).openDrawer());
    }
}