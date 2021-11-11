package com.example.visualplanner.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.databinding.EventRetailBinding;
import com.example.visualplanner.model.Event;
import com.example.visualplanner.ui.Alarm;

import java.util.Calendar;
import java.util.List;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.EventViewHolder> {

    private final List<Event> data;
    private final LayoutInflater inflater;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onModifyClick(int position);

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
        EventRetailBinding eventBinding = EventRetailBinding.inflate(inflater, parent, false);
        //View view = inflater.inflate(R.layout.event_retail, parent, false);

        return new EventViewHolder(eventBinding);
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

        private final EventRetailBinding binding;

        Alarm alarm;
        TextView dateView;
        SwitchCompat switchC;
        Event event;

        public EventViewHolder(EventRetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            dateView = itemView.findViewById(R.id.dateText);
            switchC = itemView.findViewById(R.id.setDateSwitch);
        }

        public void notifyDelete() {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onDeleteClick(position);
                }
            }
        }

        public void notifyChange() {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onModifyClick(position);
                }
            }
        }

        public void bind(Event currentEvent) {
            binding.setView(this);
            binding.setEvent(currentEvent);
            event = currentEvent;
            alarm = new Alarm(this, itemView.getContext());

            if (event.getAlarmDate() != null && event.isAlarmSet())
                dateView.setVisibility(View.VISIBLE);
            else
                dateView.setVisibility(View.GONE);
        }

        public Alarm getAlarm() {
            return alarm;
        }

        public TextView getDateView() {
            return dateView;
        }

        public SwitchCompat getSwitchC() {
            return switchC;
        }

        public Event getEvent() {
            return event;
        }
    }
}
