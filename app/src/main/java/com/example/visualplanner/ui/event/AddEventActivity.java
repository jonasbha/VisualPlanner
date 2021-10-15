package com.example.visualplanner.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.visualplanner.MainActivity;
import com.example.visualplanner.R;
import com.example.visualplanner.model.Event;
import com.example.visualplanner.repository.IEventRepository;

import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    private IEventRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        init();
    }

    private void init() {
        repo = MainActivity.getRepo();

        EditText titleInput = findViewById(R.id.eventTitleInput);
        Button submitBtn = findViewById(R.id.addEventSubmitBtn);
        submitBtn.setOnClickListener(view -> {
            Event event = new Event(titleInput.getText().toString());
            addEvent(event);
            // navigate back to fragment (change this class to fragment and do it in navigation)
        });
    }

    public void addEvent(Event e) {
        repo.addEvent(e);
    }
}