package com.iangohy.harrywinnersclientapp.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.Query;
import com.iangohy.harrywinnersclientapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iangohy.harrywinnersclientapp.model.Goal;

import java.util.logging.Logger;


public class GoalAdapter extends FirestoreAdapter<GoalAdapter.ViewHolder> {
    private static String TAG = "GoalAdapter";
    private static Resources res;

    // Pass data to constructor
    public GoalAdapter(Query query) {
        super(query);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.goal_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        res = holder.itemView.getResources();
        holder.bind(getSnapshot(position).toObject(Goal.class));
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final TextView timeLeft;
        private final ProgressBar progressBar;
        private final TextView statusString;

        public ViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.goalDescription);
            timeLeft = view.findViewById(R.id.timeLeft);
            progressBar = view.findViewById(R.id.goalProgressBar);
            statusString = view.findViewById(R.id.statusString);
        }

        public void bind(Goal goal) {
            description.setText(goal.getDescription());
            timeLeft.setText(goal.getDeadlineString());

            // Check and set colour
            if (goal.getDeadlineString().contains("years")) {
                timeLeft.setBackground(res.getDrawable(R.drawable.rounded_background_green));
            }

            // Progress text
            statusString.setText(getAmountStatus(goal.getCurrAmount(), goal.getAmount()));

            // Progress bar
            int progress = (int) (goal.getCurrAmount() / goal.getAmount() * 100);
            progressBar.setProgress(progress);
        }

        private String getAmountStatus(double current, double target) {
            String currentString = convertToAcronym(current);
            String targetString = convertToAcronym(target);
            return currentString + " of " + targetString;
        }

        private String convertToAcronym(double value) {
            if (value >= 1000000000) {
                int numeric = (int) (value / 1000000000);
                return "$" + numeric + "B";
            } else if (value >= 1000000) {
                int numeric = (int) (value / 1000000);
                return "$" + numeric + "M";
            } else if (value >= 1000) {
                int numeric = (int) (value / 1000);
                return "$" + numeric + "K";
            }
            return "$" + (int) value;
        }
    }
}
