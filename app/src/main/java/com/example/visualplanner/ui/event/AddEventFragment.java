package com.example.visualplanner.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.R;
import com.example.visualplanner.model.Event;
import com.example.visualplanner.repository.IEventRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AddEventFragment extends Fragment {

    IEventRepository repo;
    EditText titleInput;

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repo = MainActivity.getRepo();
        titleInput = view.findViewById(R.id.eventTitleInput);

        Button submitBtn = view.findViewById(R.id.addEventSubmitBtn);

        submitBtn.setOnClickListener(v -> {
            createEvent(titleInput.getText().toString());
            Navigation.findNavController(view).navigate(R.id.action_addEventFragment_to_navigation_events);
        });

        Button cancelBtn = view.findViewById(R.id.addEventCancelBtn);
        cancelBtn.setOnClickListener(v -> Navigation.findNavController(view).navigate(
                R.id.action_addEventFragment_to_navigation_events));
    }

    public void createEvent(String title) {
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
}