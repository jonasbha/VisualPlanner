package com.example.visualplanner.ui.event;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.R;
import com.example.visualplanner.adapter.EventRecycleAdapter;
import com.example.visualplanner.model.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class EventsFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EventsViewModel viewModel;

    private RecyclerView eventRecyclerView;
    private EventRecycleAdapter eventRecycleAdapter;
    private FloatingActionButton fab;

    // firestore
    private FirebaseFirestore firestoreDb;
    private CollectionReference eventCollectionReference;
    private ListenerRegistration firestoreListenerRegistration;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        firestoreDb = FirebaseFirestore.getInstance();
        eventCollectionReference =  firestoreDb.collection("events");

        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
        fab = view.findViewById(R.id.eventFab);
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(
                R.id.action_navigation_events_to_addEventFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        createFirestoreReadListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (firestoreListenerRegistration != null) {
            firestoreListenerRegistration.remove();
        }
    }

    /**
     * Updates the UI based on data in Firestore database.
     */
    private void createFirestoreReadListener() {
        Query eventsQuery = initEventCollectionDisplay();
        firestoreListenerRegistration = eventsQuery.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }

            assert value != null;
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                QueryDocumentSnapshot document = documentChange.getDocument();
                Event event = document.toObject(Event.class);

                int pos = viewModel.getEventIds().indexOf(event.getEventId());

                switch (documentChange.getType()) {
                    case ADDED:
                        if (!isDuplicate(event)) {
                            viewModel.getEvents().add(event);
                            viewModel.getEventIds().add(event.getEventId());
                            eventRecycleAdapter.notifyItemInserted(viewModel.getEvents().size() - 1);
                        }
                        break;
                    case REMOVED:
                        viewModel.getEvents().remove(pos);
                        viewModel.getEventIds().remove(pos);
                        eventRecycleAdapter.notifyItemRemoved(pos);
                        break;
                    case MODIFIED:
                        viewModel.getEvents().set(pos, event);
                        eventRecycleAdapter.notifyItemChanged(pos);
                        break;
                }

                String source = document.getMetadata().isFromCache() ?
                        "local cache" : "server";
                Log.d(TAG, "Data fetched from " + source);
            }
        });
    }

    /**
     * Simple function to prevent duplicate values on screen refresh and orientation change.
     * The function has an efficiency of O(n^2) as it is ran on for each item in list.
     */
    private boolean isDuplicate(Event event) {
        for (Event e : viewModel.getEvents()) {
            if (event.getEventId().equals(e.getEventId()))
                return true;
        }
        return false;
    }

    /**
     * The query will display the collection based on the criteria set.
     * Only the collection of the logged inn user will be displayed. The collection will be displayed
     * in a certain direction based on order of the field chosen.
     * @return Query for display of collection
     */
    private Query initEventCollectionDisplay() {
        Query query;
        // query requires indexation on fields.
        query = eventCollectionReference
                .whereEqualTo("userId", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .orderBy("alarm", Query.Direction.ASCENDING);
        return query;
    }

    private void initRecyclerView(@NonNull View view) {
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecycleAdapter = new EventRecycleAdapter(view.getContext(), viewModel.getEvents());
        eventRecyclerView.setAdapter(eventRecycleAdapter);
        eventRecyclerView.post(() -> calculateGridLayout(view));
        eventRecycleAdapter.setOnItemClickListener(new EventRecycleAdapter.OnItemClickListener() {
            @Override
            public void onModifyClick(int position) {
                modifyEvent(viewModel.getEvents().get(position));
            }

            @Override
            public void onDeleteClick(int position) {
                deleteEvent(viewModel.getEvents().get(position));
            }
        });
    }

    public void modifyEvent(Event event) {
        DocumentReference eventReference = eventCollectionReference.document(event.getEventId());

        eventReference.update(
                "alarm", event.getAlarm(),
                "dateHolder", event.getDateHolder(),
                "dateOn", event.isDateOn(),
                "timeOn", event.isTimeOn(),
                "dateSet", event.isDateSet(),
                "timeSet", event.isTimeSet()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
            else {

            }
        });
    }

    public void deleteEvent(Event event) {
        DocumentReference eventReference = eventCollectionReference.document(event.getEventId());

        eventReference.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // legg til brukerhåndtering
            } else {
                // legg til brukerhåndtering
            }
        });
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