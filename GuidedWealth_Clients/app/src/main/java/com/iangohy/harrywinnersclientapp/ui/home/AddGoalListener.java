package com.iangohy.harrywinnersclientapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.iangohy.harrywinnersclientapp.FirebaseUtil;
import com.iangohy.harrywinnersclientapp.R;
import com.iangohy.harrywinnersclientapp.model.Goal;

import java.util.Calendar;

public class AddGoalListener implements View.OnClickListener {
    private static String TAG = "HomeFragment";

    LayoutInflater mLayoutInflater;
    Context mContext;

    public AddGoalListener(LayoutInflater layoutInflater, Context context) {
        this.mLayoutInflater = layoutInflater;
        this.mContext = context;
    }

    @Override
    public void onClick(View view) {
        final View popUpGoalFragment = mLayoutInflater.inflate(R.layout.fragment_goalpopup, null);

        final EditText mGoalName = popUpGoalFragment.findViewById(R.id.popupGoalName);
        final EditText mGoalAmount = popUpGoalFragment.findViewById(R.id.popupGoalAmount);
        final EditText mGoalCurrAmount = popUpGoalFragment.findViewById(R.id.popupGoalCurrAmount);
        final DatePicker mGoalDate = popUpGoalFragment.findViewById(R.id.popupGoalTime);
        final Calendar mCalendar = Calendar.getInstance();

        final AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        mDialog.setView(popUpGoalFragment);
        mDialog.setPositiveButton("Confirm", (dialogInterface, i) -> {
            mCalendar.set(mGoalDate.getYear(), mGoalDate.getMonth(), mGoalDate.getDayOfMonth());
            mCalendar.getTime();

            Goal mGoal = new Goal(
                    Double.parseDouble(mGoalCurrAmount.getText().toString()),
                    Double.parseDouble(mGoalAmount.getText().toString()),
                    mCalendar.getTime(),
                    mGoalName.getText().toString());

            FirebaseUtil.getGoalCollection().document().set(mGoal)
                    .addOnSuccessListener(documentReference ->
                            Log.d(TAG, "Added new goal: " + mGoal.toString()))
                    .addOnFailureListener(documentReference ->
                            Log.d(TAG, "Error adding goal: " + mGoal.toString()));
        });

        mDialog.create();
        mDialog.show();
    }
}
