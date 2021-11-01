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
import com.example.visualplanner.databinding.EventRetailBinding;
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

        public EventViewHolder(EventRetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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

        public void onCheckedChanged(boolean checked) {
            EditText datePicker = itemView.findViewById(R.id.datePicker);
            if(checked)
                datePicker.setVisibility(View.VISIBLE);
            else
                datePicker.setVisibility(View.GONE);
        }

        public void bind(Event currentEvent) {
            binding.setView(this);
            binding.setEvent(currentEvent);
        }
    }
}
