package com.example.visualplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.model.Category;
import com.example.visualplanner.model.Deadline;

import java.util.List;

public class DeadlineRecycleAdapter extends RecyclerView.Adapter<DeadlineRecycleAdapter.CategoryViewHolder> {

    private List<Category> data;
    private LayoutInflater inflater;

    public DeadlineRecycleAdapter(Context context, List<Category> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.deadline_list_item, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category categoryToDisplay = data.get(position);

        holder.bind(categoryToDisplay);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1;
        private TextView tv2;
        private TextView tv3;
        private TextView tv4;
        private TextView tv5;
        private TextView tv6;
        private TextView tv7;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.deadline1);
            tv2 = itemView.findViewById(R.id.deadline2);
            tv3 = itemView.findViewById(R.id.deadline3);
            tv4 = itemView.findViewById(R.id.deadline4);
            tv5 = itemView.findViewById(R.id.deadline5);
            tv6 = itemView.findViewById(R.id.deadline6);
            tv7 = itemView.findViewById(R.id.deadline7);
        }

        public void bind(Category currentCategory) {
            for (Deadline deadline : currentCategory.getDeadlines()) {
                initTextView(deadline);

            }
        }

        public void initTextView(Deadline deadline) {
            if (deadline.getColumn() == 1) {
                tv1.setText(deadline.getDescription());
                tv1.post(() -> {
                    tv1.setPadding(tv1.getWidth(), 0, 0, 0);
                    tv2.setVisibility(View.GONE);
                    tv3.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                    tv5.setVisibility(View.GONE);
                    tv6.setVisibility(View.GONE);
                    tv7.setVisibility(View.GONE);
                });
            } else if (deadline.getColumn() == 2) {
                tv2.setText(deadline.getDescription());
            } else if (deadline.getColumn() == 3) {
                tv3.setText(deadline.getDescription());
            } else if (deadline.getColumn() == 4) {
                tv4.setText(deadline.getDescription());
            } else if (deadline.getColumn() == 5) {
                tv5.setText(deadline.getDescription());
            } else if (deadline.getColumn() == 6) {
                tv6.setText(deadline.getDescription());
            } else if (deadline.getColumn() == 7) {
                tv7.setText(deadline.getDescription());
            }
        }


  }
}
