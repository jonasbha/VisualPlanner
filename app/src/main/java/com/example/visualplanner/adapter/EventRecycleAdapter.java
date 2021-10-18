package com.example.visualplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.databinding.EventBinding;
import com.example.visualplanner.model.Event;

import java.util.List;
import java.util.Objects;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.EventViewHolder> {

    private final LiveData<List<Event>> data;
    private final LayoutInflater inflater;

    public EventRecycleAdapter(Context context, LiveData<List<Event>> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        EventBinding binding = DataBindingUtil.inflate(inflater, R.layout.event, parent, false);
        return new EventViewHolder(binding);
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

        EventBinding binding;

        public EventViewHolder(EventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setView(this);
        }

        public void removeAtPosition() {
            Objects.requireNonNull(data.getValue()).remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), data.getValue().size());
        }

        public void bind(Event currentEvent) {
            binding.setEvent(currentEvent);
            binding.executePendingBindings();
        }
    }
}
