package com.example.visualplanner.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;

public class EventsFragment extends Fragment {

    private RecyclerView eventRecyclerView;
    EventsViewModel viewModel;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setAdapter(new EventRecycleAdapter(view.getContext(), viewModel.getEvents()));
        eventRecyclerView.post(() -> calculateGridLayout(view));
    }

    private static final int cardWidth = 250;

    private void calculateGridLayout(@NonNull View view) {

        int spanCount = (int) Math.floor(eventRecyclerView.getWidth() / getCardWidthInDensityUnits());
        eventRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),
                spanCount));
    }

    private float getCardWidthInDensityUnits() {
        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        return EventsFragment.cardWidth * logicalDensity;
    }
}