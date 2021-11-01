package com.example.visualplanner.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.model.Event;

import java.util.List;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.EventViewHolder> {

    private final List<Event> data;
    private final LayoutInflater inflater;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onShowDatesClick(int position);

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
        //EventRetailBinding eventBinding = EventRetailBinding.inflate(inflater, parent, false);
        View view = inflater.inflate(R.layout.event_retail, parent, false);

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

        //private final EventRetailBinding binding;
        EditText datePicker;
        ImageView dateIcon;
        ImageView deleteIcon;
        TextView eventTitle;
        TextView dateLabel;
        TextView dateText;
        SwitchCompat setDateSwitch;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.binding = binding;
            datePicker = itemView.findViewById(R.id.datePicker);
            dateIcon = itemView.findViewById(R.id.dateIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            dateLabel = itemView.findViewById(R.id.dateLabel);
            dateText = itemView.findViewById(R.id.dateText);
            setDateSwitch = itemView.findViewById(R.id.setDateSwitch);

        }

        public void onDelete() {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onDeleteClick(position);
                }
            }
        }

        public void onShowDates() {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onShowDatesClick(position);
                }
            }
        }

        public void bind(Event currentEvent) {
            //binding.setView(this);
            //binding.setEvent(currentEvent);
            eventTitle.setText(currentEvent.getTitle());
            deleteIcon.setOnClickListener(view -> onDelete());
            setDateSwitch.setOnCheckedChangeListener((cb, on) -> {
                if(on)
                    datePicker.setVisibility(View.VISIBLE);
                else
                    datePicker.setVisibility(View.GONE);
            });
        }
    }
}
