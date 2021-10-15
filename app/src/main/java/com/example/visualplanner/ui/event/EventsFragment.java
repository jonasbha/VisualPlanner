package com.example.visualplanner.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventsFragment extends Fragment {

    private EventsViewModel viewModel;
    private EventRecycleAdapter eventRecycleAdapter;

    private RecyclerView eventRecyclerView;
    private FloatingActionButton fab;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        viewModel.getEvents().observe(getViewLifecycleOwner(), events -> eventRecycleAdapter.notifyDataSetChanged());

        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        eventRecycleAdapter = new EventRecycleAdapter(view.getContext(), viewModel.getEvents());
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setAdapter(eventRecycleAdapter);
        eventRecyclerView.post(() -> calculateGridLayout(view));

        fab = view.findViewById(R.id.eventFab);
        fab.setOnClickListener(view1 -> startActivity(new Intent(this.getContext(), AddEventActivity.class)));
    }

    private void calculateGridLayout(@NonNull View view) {

        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        /*
        int calculatedCarWidth =  RecyclerView.LayoutManager.getChildMeasureSpec(eventRecyclerView.getWidth(),
                View.MeasureSpec.UNSPECIFIED,0, ViewGroup.LayoutParams.MATCH_PARENT,false);
         */

        int cardWidth = 230 + 20; // guessed width + margin

        int spanCount = (int) Math.floor(eventRecyclerView.getWidth() / (cardWidth * logicalDensity));
        eventRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), spanCount));
    }
}