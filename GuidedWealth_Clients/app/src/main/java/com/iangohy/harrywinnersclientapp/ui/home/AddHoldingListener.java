package com.iangohy.harrywinnersclientapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.iangohy.harrywinnersclientapp.FirebaseUtil;
import com.iangohy.harrywinnersclientapp.R;
import com.iangohy.harrywinnersclientapp.model.Holding;

public class AddHoldingListener implements View.OnClickListener {
    private static String TAG = "HomeFragment";

    LayoutInflater mLayoutInflater;
    Context mContext;

    public AddHoldingListener(LayoutInflater layoutInflater, Context context) {
        this.mLayoutInflater = layoutInflater;
        this.mContext = context;
    }

    @Override
    public void onClick(View view) {
        final View popupHoldingFrag = mLayoutInflater.inflate(R.layout.fragment_holdingpopup, null);

        final EditText mHoldingSymbol = popupHoldingFrag.findViewById(R.id.popupHoldingSymbol);
        final EditText mHoldingDesc = popupHoldingFrag.findViewById(R.id.popupHoldingDesc);
        final Spinner mHoldingBuySell = popupHoldingFrag.findViewById(R.id.popupHoldingBuySell);
        final EditText mHoldingAmount = popupHoldingFrag.findViewById(R.id.popupHoldingAmount);

        final AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        mDialog.setView(popupHoldingFrag);

        mDialog.setPositiveButton("Confirm", (dialogInterface, i) -> {
            String symbol = mHoldingSymbol.getText().toString();
            String desc = mHoldingDesc.getText().toString();

            Boolean managed = false;

            FirebaseUtil.getHoldingsCollection().document(symbol).get().addOnCompleteListener(task -> {
                double delta = Double.parseDouble(mHoldingAmount.getText().toString());
                if (mHoldingBuySell.getSelectedItem().toString().equals("Sell")) {
                    delta *= -1;
                }

                double quantity = 0;
                if (task.isSuccessful() && task.getResult().exists()) {
                    quantity = task.getResult().toObject(Holding.class).getQuantity();
                }
                quantity += delta;

                Holding mHolding = new Holding(desc, symbol, quantity, managed);
                FirebaseUtil.getHoldingsCollection().document(symbol).set(mHolding)
                        .addOnSuccessListener(documentReference ->
                                Log.d(TAG, "Added new holding: " + mHolding.toString()))
                        .addOnFailureListener(documentReference ->
                                Log.d(TAG, "Error adding holding: " + mHolding.toString()));
            });
        });

        mDialog.create();
        mDialog.show();
    }
}
