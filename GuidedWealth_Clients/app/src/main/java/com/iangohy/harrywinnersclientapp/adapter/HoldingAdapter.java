package com.iangohy.harrywinnersclientapp.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.Query;
import com.iangohy.harrywinnersclientapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iangohy.harrywinnersclientapp.model.Holding;


public class HoldingAdapter extends FirestoreAdapter<HoldingAdapter.ViewHolder> {
    private static String TAG = "HoldingAdapter";
    private static Resources res;

    // Pass data to constructor
    public HoldingAdapter(Query query) {
        super(query);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.portfolio_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        res = holder.itemView.getResources();
        holder.bind(getSnapshot(position).toObject(Holding.class));
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView assetDescription;
        private final TextView amount;
        private final TextView heldBy;
        private final TextView pnl;

        public ViewHolder(View view) {
            super(view);
            assetDescription = (TextView) view.findViewById(R.id.assetDescription);
            amount = (TextView) view.findViewById(R.id.amount);
            heldBy = (TextView) view.findViewById(R.id.heldBy);
            pnl = (TextView) view.findViewById(R.id.pnl);
        }

        public void bind(Holding holding) {
            assetDescription.setText(holding.getSymbol());
            amount.setText(String.valueOf(holding.getQuantity()));
            if (!holding.getManaged()) {
                heldBy.setBackground(res.getDrawable(R.drawable.rounded_background_orange));
                heldBy.setText("External");
            }

            String pnlString = String.format("%.2f", holding.getDelta());
            if (holding.getDelta() < 0) {
                pnlString = "▼ " + pnlString;
                pnl.setTextColor(res.getColor(R.color.loss_red));
            } else {
                pnlString = "▲ " + pnlString;
            }
            pnl.setText(pnlString);
        }
    }
}
