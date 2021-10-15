package com.example.visualplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.model.Event;

import java.util.List;
import java.util.Objects;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.EventViewHolder> {

    private LiveData<List<Event>> data;
    private final LayoutInflater inflater;

    public EventRecycleAdapter(Context context, LiveData<List<Event>> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.event, parent, false);

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event eventToDisplay = Objects.requireNonNull(data.getValue()).get(position);

        holder.bind(eventToDisplay);
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(data.getValue()).size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView time;
        TextView date;
        TextView location;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventTitle);
            description = itemView.findViewById(R.id.eventDescription);
            time = itemView.findViewById(R.id.timeInput);
            date = itemView.findViewById(R.id.dateInput);
            location = itemView.findViewById(R.id.locationInput);
        }

        public void bind(Event currentEvent) {
            title.setText(currentEvent.getTitle());
        }
    }
}
