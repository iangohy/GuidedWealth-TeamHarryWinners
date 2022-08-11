package com.iangohy.harrywinnersclientapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import com.iangohy.harrywinnersclientapp.R;
import com.iangohy.harrywinnersclientapp.model.Recommendation;

import java.util.ArrayList;
import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
    private static final String TAG = "RecommendationAdapter";
    private List<Recommendation> recommendations; // list of recommendations
    private Context context;

    public RecommendationAdapter(Context context, List<Recommendation> dataset) {
        this.recommendations = dataset;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recommendation_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get element from dataset at this position and replace contents of the view
        Recommendation recommendation = recommendations.get(position);
        TextView description = holder.getDescription();
        TextView badgeDescription = holder.getBadgeDescription();
        TextView currentValueText = holder.getCurrentValueText();
        TextView proposedValueText = holder.getProposedValueText();

        description.setText(recommendation.getDescription());
        String badgeVal;
        Drawable badgeBackground;
        if (recommendation.isManaged()) {
            badgeVal = "Managed";
            badgeBackground = AppCompatResources.getDrawable(this.context, R.drawable.rounded_background_blue);

        } else {
            badgeVal = "External";
            badgeBackground = AppCompatResources.getDrawable(this.context, R.drawable.rounded_background_orange);
        }
        badgeDescription.setText(badgeVal);
        badgeDescription.setBackground(badgeBackground);
        if (recommendation.isManaged()) badgeDescription.setTextColor(this.context.getResources().getColor(R.color.white));
        currentValueText.setText(String.valueOf(recommendation.getCurrentValue()));
        boolean toBuy = recommendation.isToBuy();
        int proposedTextColor = toBuy ?
                this.context.getResources().getColor(R.color.profit_green) :
                this.context.getResources().getColor(R.color.loss_red);
        String proposedText = (toBuy ? "+" : "-") + recommendation.getProposedValue();
        proposedValueText.setText(proposedText);
        proposedValueText.setTextColor(proposedTextColor);
    }

    @Override
    public int getItemCount() {
        return recommendations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final TextView badgeDescription;
        private final TextView currentValueText;
        private final TextView proposedValueText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.recommendation_description);
            badgeDescription = itemView.findViewById(R.id.recommendation_badge);
            currentValueText = itemView.findViewById(R.id.recommendation_current_value);
            proposedValueText = itemView.findViewById(R.id.recommendation_proposed_value);
        }

        public TextView getCurrentValueText() {
            return currentValueText;
        }

        public TextView getProposedValueText() {
            return proposedValueText;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getBadgeDescription() {
            return badgeDescription;
        }
    }

    public void updateDataset(List<Recommendation> newData) {
       this.recommendations = new ArrayList<>(newData);
       this.notifyDataSetChanged();
    }
}
