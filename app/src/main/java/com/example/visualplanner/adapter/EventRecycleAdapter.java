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

        Calendar calendar;
        int year, month, day;
        TextView dateText;
        DatePickerDialog datePickerDialog;
        SwitchCompat switchC;
        Event event;

        public EventViewHolder(EventRetailBinding binding) {
            super(binding.getRoot());
            Log.d("hey", "created");
            this.binding = binding;

            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            dateText = itemView.findViewById(R.id.dateText);
            switchC = itemView.findViewById(R.id.setDateSwitch);

            datePickerDialog = new DatePickerDialog(
                    itemView.getContext(),
                    (DatePickerDialog.OnDateSetListener) (datePicker, year, month, day) -> {
                        calendar.set(year, month, day);
                        event.setAlarmDate(calendar.getTime());
                        switchC.setChecked(true);
                        notifyChange();
                    }, year, month, day
            );
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

        public void showDatePicker() {
            datePickerDialog.show();
        }

        public void onCheckedChanged(boolean checked) {
            if (checked) {
                dateText.setVisibility(View.VISIBLE);
                if (event.getAlarmDate() == null) {
                    switchC.setChecked(false);
                    showDatePicker();
                }
                if (event.getAlarmDate() != null)
                    event.setAlarmSet(true);

                if (!datePickerDialog.isShowing())
                    notifyChange();
            } else {
                dateText.setVisibility(View.GONE);
                event.setAlarmSet(false);
                notifyChange();
            }
        }

        public void bind(Event currentEvent) {
            binding.setView(this);
            binding.setEvent(currentEvent);
            event = currentEvent;

            if (event.getAlarmDate() != null && event.isAlarmSet())
                dateText.setVisibility(View.VISIBLE);
            else
                dateText.setVisibility(View.GONE);
        }
    }
}
