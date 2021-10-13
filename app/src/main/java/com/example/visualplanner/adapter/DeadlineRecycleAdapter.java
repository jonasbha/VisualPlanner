package com.example.visualplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.model.Category;
import com.example.visualplanner.model.Deadline;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
        }

        public void bind(Category currentCategory) {

            LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.deadline_list_item, (ViewGroup) itemView);

            ArrayList<Deadline> deadlines = currentCategory.getDeadlines();

            tv1 = (TextView) inflater.inflate(R.layout.textviews, null);
            tv1.setText(deadlines.get(0).getDescription());

            linearLayout.addView(tv1, 0 ,new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
  }
}
