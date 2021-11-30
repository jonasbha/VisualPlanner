package com.example.visualplanner.ui.event;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    // firestore
    private FirebaseFirestore db;
    private CollectionReference eventCollectionReference;
    private ListenerRegistration firestoreListenerRegistration;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        db = FirebaseFirestore.getInstance();
        eventCollectionReference =  db.collection("events");

        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
        fab = view.findViewById(R.id.eventFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent();
            }
        });
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
                .orderBy("alarm.dateTime", Query.Direction.ASCENDING);
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
                "alarm.dateTime", event.getAlarm().getDateTime(),
                "alarm.alarmHolder", event.getAlarm().getAlarmHolder(),
                "alarm.dateHolder", event.getAlarm().getDateHolder(),
                "alarm.timeHolder", event.getAlarm().getTimeHolder(),
                "alarm.dateOn", event.getAlarm().isDateOn(),
                "alarm.timeOn", event.getAlarm().isTimeOn(),
                "alarm.dateSet", event.getAlarm().isDateSet(),
                "alarm.timeSet", event.getAlarm().isTimeSet()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
            else {

            }
        });
    }

    public void deleteEvent(Event event) {
        DocumentReference eventReference = eventCollectionReference.document(event.getEventId());
        eventReference.delete();
    }

    public void createEvent() {
        DocumentReference eventReference = db.collection("events").document();
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        dialogBuilder = new AlertDialog.Builder(this.getContext());
        final View popupView = getLayoutInflater().inflate(R.layout.create_event_popup, null);
        dialogBuilder.setView(popupView);
        dialogBuilder.setTitle(R.string.createEvent);
        dialogBuilder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            dialog.cancel();
        });
        dialogBuilder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            EditText et = popupView.findViewById(R.id.eventTitleInput);
            String title = et.getText().toString();

            Event event = new Event(title);
            event.setEventId(eventReference.getId());
            event.setUserId(userId);
            eventReference.set(event);

            dialog.dismiss();
        });
        dialog = dialogBuilder.create();
        dialog.show();
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