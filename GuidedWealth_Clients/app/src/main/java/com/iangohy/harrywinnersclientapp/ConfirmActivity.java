package com.iangohy.harrywinnersclientapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.iangohy.harrywinnersclientapp.model.Banker;
import com.iangohy.harrywinnersclientapp.model.Client;

public class ConfirmActivity extends AppCompatActivity {

    private final String BANKER_REQUEST_NAME = "requests";
    private final String TAG = "ConfirmActivity";
    private ImageButton backBtn;
    private Button confirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        String banker = getIntent().getStringExtra("banker");

        backBtn = findViewById(R.id.backBtn);
        confirmBtn = findViewById(R.id.confirmBtn);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ConfirmActivity.this, ReviewActivity.class);
            startActivity(intent);
        });

        confirmBtn.setOnClickListener(view -> {
            FirebaseUtil.getUserDocument().get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        FirebaseUtil.getBankerCollection().document(banker)
                            .collection(BANKER_REQUEST_NAME).document(FirebaseUtil.getUname()).set(documentSnapshot.toObject(Client.class));
                    }
                } else {
                    Log.e(TAG, "failed with " + task.getException());
                }
            });

            FirebaseUtil.getBankerCollection().document(banker).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        FirebaseUtil.getPrevBankerCollection().document(banker).set(documentSnapshot.toObject(Banker.class));
                    }
                } else {
                    Log.e(TAG, "failed with " + task.getException());
                }
            });

            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
            intent.putExtra("sent", true);
            startActivity(intent);
        });

        String message;
        if (banker != null) {
            message = "Your portfolio will be sent to " + banker + " for review.  Should " + banker + " be unable to reply within 2 business days, we will inform you and seek your consent to match you with another banker.";
        } else {
            message = "Our matching algorithms will match you with a suitable banker. You can expect a reply within 3 business days.";
        }
        ((TextView) findViewById(R.id.confirmMessage)).setText(message);
    }
}
