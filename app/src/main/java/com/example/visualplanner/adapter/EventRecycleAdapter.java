package com.example.visualplanner.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.model.Event;

import java.util.List;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.EventViewHolder> {

    private final List<Event> data;
    private final LayoutInflater inflater;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onUpdateClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public EventRecycleAdapter(Context context, List<Event> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("EventRecyclerAdapter", "Creating View");
        View view = inflater.inflate(R.layout.event, parent, false);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Log.d("EventRecyclerAdapter", "Binding View $position");
        Event eventToDisplay = data.get(position);
        holder.bind(eventToDisplay);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView timeInput;
        TextView dateInput;
        TextView locationInput;
        ImageView alarmIcon;
        ImageView locationIcon;
        ImageView calendarIcon;
        ImageButton deleteIcon;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            timeInput = itemView.findViewById(R.id.timeInput);
            dateInput = itemView.findViewById(R.id.dateInput);
            locationInput = itemView.findViewById(R.id.locationInput);
            alarmIcon = itemView.findViewById(R.id.alarmIcon);
            locationIcon = itemView.findViewById(R.id.locationIcon);
            calendarIcon = itemView.findViewById(R.id.calendarIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);

            deleteIcon.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onDeleteClick(position);
                    }
                }
            });
        }

        public void bind(Event currentEvent) {
            eventTitle.setText(currentEvent.getTitle());
        }
    }
}
