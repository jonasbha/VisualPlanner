package com.example.visualplanner.ui.deadline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.R;
import com.example.visualplanner.adapter.DeadlineRecycleAdapter;

public class DeadlineListFragment extends Fragment {

    private RecyclerView deadlineRecyclerView;
    DeadlinesViewModel viewModel;

    public DeadlineListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DeadlinesViewModel.class);
        return inflater.inflate(R.layout.fragment_deadline_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deadlineRecyclerView = view.findViewById(R.id.deadlineRecyclerView);
        deadlineRecyclerView.setAdapter(new DeadlineRecycleAdapter(view.getContext(), viewModel.getCategories()));
        deadlineRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        ImageButton drawerBtn = view.findViewById(R.id.openDrawerBtn);
        drawerBtn.setOnClickListener(v -> ((MainActivity) requireActivity()).openDrawer());
    }
}