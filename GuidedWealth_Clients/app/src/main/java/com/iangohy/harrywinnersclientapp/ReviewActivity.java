package com.iangohy.harrywinnersclientapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.iangohy.harrywinnersclientapp.adapter.BankerProfileAdapter;

public class ReviewActivity extends AppCompatActivity {

    private String TAG = "ReviewActivity";
    private ImageButton backBtn;
    private Button matchBtn;

    private BankerProfileAdapter previousBankerAdapter;
    private BankerProfileAdapter suggestedBankerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        backBtn = findViewById(R.id.backBtn);
        matchBtn = findViewById(R.id.confirmBtn);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
            startActivity(intent);
        });

        matchBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ReviewActivity.this, ConfirmActivity.class);
            startActivity(intent);
        });

        // Previous interaction recycler
        RecyclerView pastRecycler = findViewById(R.id.previousRecycler);
        pastRecycler.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.HORIZONTAL, false));
        previousBankerAdapter = new BankerProfileAdapter(FirebaseUtil.getPrevBankerCollection());
        pastRecycler.setAdapter(previousBankerAdapter);

        // suggestions recycler
        RecyclerView suggestedRecycler = findViewById(R.id.suggestedRecycler);
        suggestedRecycler.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.HORIZONTAL, false));
        suggestedBankerAdapter = new BankerProfileAdapter(FirebaseUtil.getBankerCollection().orderBy("karma", Query.Direction.DESCENDING));
        suggestedRecycler.setAdapter(suggestedBankerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (previousBankerAdapter != null) {
            previousBankerAdapter.startListening();
        }
        if (suggestedBankerAdapter != null) {
            suggestedBankerAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (previousBankerAdapter != null) {
            previousBankerAdapter.stopListening();
        }
        if (suggestedBankerAdapter != null) {
            suggestedBankerAdapter.stopListening();
        }
    }
}