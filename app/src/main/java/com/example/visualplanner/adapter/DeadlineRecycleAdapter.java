package com.example.visualplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visualplanner.R;
import com.example.visualplanner.model.Category;

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

        View itemView = inflater.inflate(R.layout.deadline, parent, false);

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

            // forsøk på å manipulere riktige visninger av deadline_list_item ved å legge til views dynamisk.

            /*
                LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.deadline_list_item, (ViewGroup) itemView);
            LinearLayout tvCollection = (LinearLayout) inflater.inflate(R.layout.textview_collection, null);
            LinearLayout weekdays = (LinearLayout) inflater.inflate(R.layout.weekdays, null);

            TextView weekday = weekdays.findViewById(R.id.textView);
            weekday.post(() -> System.out.println(weekday.getWidth()));

            ArrayList<Deadline> deadlines = currentCategory.getDeadlines();

            tv1 = tvCollection.findViewById(R.id.tv1);
            tvCollection.removeView(tvCollection.findViewById(R.id.tv1));
            tv1.setText(deadlines.get(0).getDescription());
            linearLayout.addView(tv1, 0 ,new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
             */

        }
    }
}
