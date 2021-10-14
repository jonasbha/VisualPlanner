package com.example.visualplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.model.Category;
import com.example.visualplanner.model.Event;

import java.util.List;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.EventViewHolder> {

    private List<Event> data;
    private LayoutInflater inflater;

    public EventRecycleAdapter(Context context, List<Event> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.event_list_item, parent, false);

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event eventToDisplay = data.get(position);

        holder.bind(eventToDisplay);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView time;
        TextView date;
        TextView location;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.eventDescription);
            time = itemView.findViewById(R.id.timeInput);
            date = itemView.findViewById(R.id.dateInput);
            location = itemView.findViewById(R.id.locationInput);
        }

        public void bind(Event currentEvent) {
            description.setText(currentEvent.getDescription());
        }
    }
}
