package com.example.visualplanner.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;

import java.util.Objects;

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

        ViewTreeObserver viewTreeObserver = eventRecyclerView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this::calculateSize);

        eventRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),
                1));

    }

    private static final int sColumnWidth = 250; // hardkodet atm

    private void calculateSize() {
        int spanCount = (int) Math.floor(eventRecyclerView.getWidth() / convertDPToPixels(sColumnWidth));
        ((GridLayoutManager) Objects.requireNonNull(eventRecyclerView.getLayoutManager())).setSpanCount(spanCount);
    }

    private float convertDPToPixels(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }
}