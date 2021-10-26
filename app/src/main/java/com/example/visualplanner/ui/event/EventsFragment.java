package com.example.visualplanner.ui.event;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventsFragment extends Fragment implements IEventFirestore {

    private EventsViewModel viewModel;

    private RecyclerView eventRecyclerView;
    private EventRecycleAdapter eventRecycleAdapter;
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

        getEvents();
        initRecyclerView(view);

        fab = view.findViewById(R.id.eventFab);
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(
                R.id.action_navigation_events_to_addEventFragment));

        createNewEvent("Tannlegetime");
        //createNewEvent("Raid.");
        //createNewEvent("Rydde rommet.");
    }

    @Override
    public void createNewEvent(String title) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference newEventReference = db.collection("events").document();
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Event event = new Event(title);
        event.setEventId(newEventReference.getId());
        event.setUserId(userId);

        newEventReference.set(event).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // legg til brukerhåndtering
            } else {
                // legg til brukerhåndtering
            }
        });
    }

    private void getEvents() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventsCollectionRef = db.collection("events");

        Query eventsQuery = eventsCollectionRef.
                whereEqualTo("userId", Objects.requireNonNull(
                        FirebaseAuth.getInstance().getCurrentUser()).getUid()
                );

        eventsQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    Event event = document.toObject(Event.class);
                    viewModel.addEvent(event);
                }
                eventRecycleAdapter.notifyDataSetChanged();
            } else {
                // legg til brukerhåndtering
            }
        });
    }

    private void initRecyclerView(@NonNull View view) {
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);

        if (eventRecycleAdapter == null) {
            eventRecycleAdapter = new EventRecycleAdapter(view.getContext(), viewModel.getEvents());
        }
        eventRecyclerView.setAdapter(eventRecycleAdapter);
        eventRecyclerView.post(() -> calculateGridLayout(view));
    }

    private void calculateGridLayout(@NonNull View view) {

        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        /*
        int calculatedCarWidth =  RecyclerView.LayoutManager.getChildMeasureSpec(eventRecyclerView.getWidth(),
                View.MeasureSpec.UNSPECIFIED,0, ViewGroup.LayoutParams.MATCH_PARENT,false);
         */

        int cardWidth = 250 + 20; // guessed width + margin

        int spanCount = (int) Math.floor(eventRecyclerView.getWidth() / (cardWidth * logicalDensity));
        eventRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), spanCount));
    }
}